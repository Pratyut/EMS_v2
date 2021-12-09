package com.example.splashscreenlotteanimation.Pojo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Timesheet {

    // string variable for
    // storing employee name.
    private String Date;

    // string variable for storing
    // employee contact number
    private List<String> Time = new ArrayList();

    // string variable for storing
    // employee address.
    private List<String> TaskDescreption = new ArrayList();

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public Timesheet() { }

    public List<String> getTaskDescreption() {
        return TaskDescreption;
    }

    public void setTaskDescreption(List<String> taskDescreption) {
        TaskDescreption = taskDescreption;
    }

    public List<String> getTime() {
        return Time;
    }

    public void setTime(List<String> time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}