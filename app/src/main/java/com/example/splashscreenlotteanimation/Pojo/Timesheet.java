package com.example.splashscreenlotteanimation.Pojo;

public class Timesheet {

    // string variable for
    // storing employee name.
    private String Date;

    // string variable for storing
    // employee contact number
    private String[] Time;

    // string variable for storing
    // employee address.
    private String[] TaskDescreption;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public Timesheet() {
        this.Time = new String[8];
        this.TaskDescreption = new String[8];
    }

    // created getter and setter methods
    // for all our variables.
    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String[] getTime() {
        return Time;
    }

    public void setTime(String[] Time) {
        this.Time = Time;
    }

    public String[] getTaskDescreption() {
        return TaskDescreption;
    }

    public void setTaskDescreption(String[] TaskDescreption) {
        this.TaskDescreption = TaskDescreption;
    }
}