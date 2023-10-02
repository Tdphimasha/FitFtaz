package com.himasha.fitfatz;

public class BMICalculator {
    // Method to calculate BMI based on weight and height
    public static double calculateBMI(double weight, double height) {
        double heightInMeters = height / 100; // Convert height from centimeters to meters
        return weight / (heightInMeters * heightInMeters);
    }
    // Method to determine BMI category based on BMI value
    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";// BMI less than 18.5 is considered underweight
        } else if (bmi < 25) {
            return "Normal weight";// BMI between 18.5 and 24.9 is considered normal weight
        } else if (bmi < 30) {
            return "Overweight";// BMI between 25 and 29.9 is considered overweight
        } else {
            return "Obese"; // BMI of 30 or greater is considered obese
        }
    }
}
