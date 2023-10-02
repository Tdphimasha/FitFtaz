package com.himasha.fitfatz;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Meal_tracker extends AppCompatActivity {
    EditText editTask, editTask2, editTask3, updateTaskId;
    Button btnCreateTask, btnReadTask, btnUpdateTask, btnDeleteTask;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_tracker);

        databaseHelper = new DatabaseHelper(this);
        editTask = (EditText) findViewById(R.id.editTextName);
        editTask2 = (EditText) findViewById(R.id.editTextName2);
        editTask3 = (EditText) findViewById(R.id.editTextName3);
        updateTaskId = (EditText) findViewById(R.id.editTextTextPersonName2);
        btnCreateTask = (Button) findViewById(R.id.button);
        btnReadTask = (Button) findViewById(R.id.button2);
        btnUpdateTask = (Button) findViewById(R.id.button3);
        btnDeleteTask = (Button) findViewById(R.id.button4);

        createTask();
        readTask();
        updateTask();
        deleteTask();
    }

    // Inserting a task
    private void createTask() {
        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCreated = databaseHelper.createTask(editTask.getText().toString(), editTask2.getText().toString(), editTask3.getText().toString());
                if (isCreated) {
                    Toast.makeText(Meal_tracker.this, "Task inserted Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Meal_tracker.this, "Task not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Reading the available tasks
    public void readTask() {
        btnReadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = databaseHelper.readTask();
                if (data.getCount() == 0) {
                    showMessage("Error", "Unavailable Tasks");
                    return;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()) {
                        buffer.append("ID : " + data.getString(0) + "\n");
                        buffer.append("NAME : " + data.getString(1) + "\n\n");
                        buffer.append("NAME2 : " + data.getString(2) + "\n\n");
                        buffer.append("NAME3 : " + data.getString(3) + "\n\n");
                    }
                    showMessage("Tasks in database", buffer.toString());
                }
            }
        });
    }

    // Showing an alert dialog with a message
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }

    // Updating a task
    public void updateTask() {
        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = updateTaskId.getText().toString();
                String name = editTask.getText().toString();
                String name2 = editTask2.getText().toString();
                String name3 = editTask3.getText().toString();

                boolean isUpdated = databaseHelper.updateTasks(id, name, name2, name3);
                if (isUpdated) {
                    Toast.makeText(Meal_tracker.this, "Tasks Updated failed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Meal_tracker.this, "Task updated Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Deleting a task
    public void deleteTask() {
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = updateTaskId.getText().toString();

                int deletedRow = databaseHelper.deleteTasks(id);
                if (deletedRow > 0) {
                    Toast.makeText(Meal_tracker.this, "Task deleted Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Meal_tracker.this, "Delete Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
