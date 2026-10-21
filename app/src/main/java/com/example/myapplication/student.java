package com.example.myapplication;

public class student {
    private String firstName;
    private String lastName;
    private String email;
    private String grade;
    private String major;
    private boolean agreeTerms;
    private boolean agreeNotifications;

    public student(String firstName, String lastName, String email, String grade, String major, boolean agreeTerms, boolean agreeNotifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.major = major;
        this.agreeTerms = agreeTerms;
        this.agreeNotifications = agreeNotifications;
    }

    public String getFormattedDetails() {
        return "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Grade: " + grade + "\n" +
                "Major: " + major + "\n" +
                "Notifications: " + (agreeNotifications ? "Yes" : "No");
    }
}