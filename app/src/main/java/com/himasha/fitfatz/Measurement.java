package com.himasha.fitfatz;

import java.util.Date;

class Measurement<T> {
    final Date timestamp; // Timestamp indicating when the measurement was taken
    final T measurement; // The measurement value

    // Constructor for creating a Measurement instance
    Measurement(Date timestamp, T measurement) {
        this.timestamp = timestamp;
        this.measurement = measurement;
    }
}
