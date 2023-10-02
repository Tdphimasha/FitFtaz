package com.himasha.fitfatz;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

class MeasureStore {
    private final CopyOnWriteArrayList<Measurement<Integer>> measurements = new CopyOnWriteArrayList<>();
    private int minimum = 2147483647; // Initialize minimum with the maximum possible integer value
    private int maximum = -2147483648; // Initialize maximum with the minimum possible integer value

    @SuppressWarnings("FieldCanBeLocal")
    private final int rollingAverageSize = 4;

    void add(int measurement) {
        Measurement<Integer> measurementWithDate = new Measurement<>(new Date(), measurement);

        measurements.add(measurementWithDate);
        if (measurement < minimum) minimum = measurement; // Update minimum if necessary
        if (measurement > maximum) maximum = measurement; // Update maximum if necessary
    }
    CopyOnWriteArrayList<Measurement<Float>> getStdValues() {
        CopyOnWriteArrayList<Measurement<Float>> stdValues = new CopyOnWriteArrayList<>();

        for (int i = 0; i < measurements.size(); i++) {
            int sum = 0;
            for (int rollingAverageCounter = 0; rollingAverageCounter < rollingAverageSize; rollingAverageCounter++) {
                sum += measurements.get(Math.max(0, i - rollingAverageCounter)).measurement;
            }
            // Calculate the standardized value based on rolling average, minimum, and maximum
            Measurement<Float> stdValue =
                    new Measurement<>(
                            measurements.get(i).timestamp,
                            ((float)sum / rollingAverageSize - minimum ) / (maximum - minimum));
            stdValues.add(stdValue);
        }
        return stdValues;
    }
    @SuppressWarnings("SameParameterValue")
    CopyOnWriteArrayList<Measurement<Integer>> getLastStdValues(int count) {
        if (count < measurements.size()) {
            // Return a sublist of the last 'count' measurements
            return  new CopyOnWriteArrayList<>(measurements.subList(measurements.size() - 1 - count, measurements.size() - 1));
        } else {
            // Return all measurements if 'count' is greater than or equal to the size of measurements
            return measurements;
        }
    }
    Date getLastTimestamp() {
        // Return the timestamp of the last measurement
        return measurements.get(measurements.size() - 1).timestamp;
    }
}
