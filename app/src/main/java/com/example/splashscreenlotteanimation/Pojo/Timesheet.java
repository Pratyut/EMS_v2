/*
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
}*/
//Older Timesheet class code is shelved above.

package com.example.splashscreenlotteanimation.Pojo;
public class Timesheet {
   public String emp_id,approver_id,date,summary,description,hours,timesheet_id,status;

    public Timesheet(String emp_id, String approver_id, String date, String summary, String description,String hours,String timesheet_id) {
        this.emp_id = emp_id;
        this.approver_id = approver_id;
        this.date = date;
        this.summary = summary;
        this.description = description;
        this.hours=hours;
        this.timesheet_id=timesheet_id;
        this.status="pending";
    }

    public Timesheet() {
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(String approver_id) {
        this.approver_id = approver_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}