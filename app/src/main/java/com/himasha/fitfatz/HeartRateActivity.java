package com.himasha.fitfatz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HeartRateActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private OutputAnalyzer analyzer;
    private final int REQUEST_CODE_CAMERA = 0;
    public static final int MESSAGE_UPDATE_REALTIME = 1;
    public static final int MESSAGE_UPDATE_FINAL = 2;
    public static final int MESSAGE_CAMERA_NOT_AVAILABLE = 3;
    private static final int MENU_INDEX_NEW_MEASUREMENT = 0;
    private static final int MENU_INDEX_EXPORT_RESULT = 1;
    private static final int MENU_INDEX_EXPORT_DETAILS = 2;

    public enum VIEW_STATE {
        MEASUREMENT,
        SHOW_RESULTS
    }

    private boolean justShared = false;

    // Handler to update the UI from background threads
    @SuppressLint("HandlerLeak")
    private final Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            // Handle different message types received from background threads
            if (msg.what ==  MESSAGE_UPDATE_REALTIME) {
                // Update the real-time heart rate value on the UI
                ((TextView) findViewById(R.id.textView)).setText(msg.obj.toString());
            }

            if (msg.what == MESSAGE_UPDATE_FINAL) {
                // Update the final heart rate value on the UI
                ((EditText) findViewById(R.id.editText)).setText(msg.obj.toString());

                // Change the view state to show the results
                setViewState(VIEW_STATE.SHOW_RESULTS);
            }

            if (msg.what == MESSAGE_CAMERA_NOT_AVAILABLE) {
                // Handle the case when the camera is not available
                Log.println(Log.WARN, "camera", msg.obj.toString());

                // Show a warning message on the UI
                ((TextView) findViewById(R.id.textView)).setText(
                        R.string.camera_not_found
                );

                // Stop the heart rate measurement process
                analyzer.stop();
            }
        }
    };

    // Service responsible for camera operations
    private final CameraService cameraService = new CameraService(this, mainHandler);

    @Override
    protected void onResume() {
        super.onResume();

        // Create an instance of OutputAnalyzer for heart rate measurement
        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);

        TextureView cameraTextureView = findViewById(R.id.textureView2);
        SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();

        // Start the camera preview and heart rate measurement process
        if ((previewSurfaceTexture != null) && !justShared) {
            Surface previewSurface = new Surface(previewSurfaceTexture);

            // Show a warning when the device doesn't have a flash
            if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Snackbar.make(
                        findViewById(R.id.constraintLayout),
                        getString(R.string.noFlashWarning),
                        Snackbar.LENGTH_LONG
                ).show();
            }

            // Hide the "New Measurement" item while another one is in progress
            ((Toolbar) findViewById(R.id.toolbar)).getMenu().getItem(MENU_INDEX_NEW_MEASUREMENT).setVisible(false);

            // Start the camera and heart rate measurement
            cameraService.start(previewSurface);
            analyzer.measurePulse(cameraTextureView, cameraService);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop the camera and heart rate measurement
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        // Request camera permission from the user
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Show a snackbar message if camera permission is not granted
                Snackbar.make(
                        findViewById(R.id.constraintLayout),
                        getString(R.string.cameraPermissionRequired),
                        Snackbar.LENGTH_LONG
                ).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i("MENU", "menu is being prepared");

        // Inflate the menu layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    // Set the visibility and state of UI elements based on the view state
    public void setViewState(VIEW_STATE state) {
        Menu appMenu = ((Toolbar) findViewById(R.id.toolbar)).getMenu();
        switch (state) {
            case MEASUREMENT:
                appMenu.getItem(MENU_INDEX_NEW_MEASUREMENT).setVisible(false);
                appMenu.getItem(MENU_INDEX_EXPORT_RESULT).setVisible(false);
                appMenu.getItem(MENU_INDEX_EXPORT_DETAILS).setVisible(false);
                findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
                break;
            case SHOW_RESULTS:
                findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                appMenu.getItem(MENU_INDEX_EXPORT_RESULT).setVisible(true);
                appMenu.getItem(MENU_INDEX_EXPORT_DETAILS).setVisible(true);
                appMenu.getItem(MENU_INDEX_NEW_MEASUREMENT).setVisible(true);
                break;
        }
    }

    // Called when "New Measurement" menu item is clicked
    public void onClickNewMeasurement(MenuItem item) {
        onClickNewMeasurement();
        finish();
    }

    // Called when "New Measurement" floating action button is clicked
    public void onClickNewMeasurement(View view) {
        onClickNewMeasurement();
        finish();
    }

    // Start a new heart rate measurement
    public void onClickNewMeasurement() {
        // Create a new instance of OutputAnalyzer
        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);

        // Clear prior results
        char[] empty = new char[0];
        ((EditText) findViewById(R.id.editText)).setText(empty, 0, 0);
        ((TextView) findViewById(R.id.textView)).setText(empty, 0, 0);

        // Change the view state to measurement
        setViewState(VIEW_STATE.MEASUREMENT);

        TextureView cameraTextureView = findViewById(R.id.textureView2);
        SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();

        if (previewSurfaceTexture != null) {
            Surface previewSurface = new Surface(previewSurfaceTexture);
            // Start the camera and heart rate measurement
            cameraService.start(previewSurface);
            analyzer.measurePulse(cameraTextureView, cameraService);
            finish();
        }
    }
    // Called when "Export Result" menu item is clicked
    public void onClickExportResult(MenuItem item) {
        final Intent intent = getTextIntent((String) ((TextView) findViewById(R.id.textView)).getText());
        justShared = true;
        startActivity(Intent.createChooser(intent, getString(R.string.send_output_to)));
        finish();
    }
    // Called when "Export Details" menu item is clicked
    public void onClickExportDetails(MenuItem item) {
        final Intent intent = getTextIntent(((EditText) findViewById(R.id.editText)).getText().toString());
        justShared = true;
        startActivity(Intent.createChooser(intent, getString(R.string.send_output_to)));
        finish();
    }
    // Create an intent to share the output text
    private Intent getTextIntent(String intentText) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                String.format(
                        getString(R.string.output_header_template),
                        new SimpleDateFormat(
                                getString(R.string.dateFormat),
                                Locale.getDefault()
                        ).format(new Date())
                ));
        intent.putExtra(Intent.EXTRA_TEXT, intentText);
        return intent;
    }
}
