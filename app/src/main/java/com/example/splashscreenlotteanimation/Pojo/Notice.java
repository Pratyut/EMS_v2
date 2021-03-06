package com.example.splashscreenlotteanimation.Pojo;

public class Notice
{
    // string variable for
    // storing employee name.
    private String Receiver;

    // string variable for storing
    // employee contact number
    private String Subject;

    // string variable for storing
    // employee address.
    private String Body;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public Notice() {

    }

    public Notice(String receiver, String subject, String body) {
        Receiver = receiver;
        Subject = subject;
        Body = body;
    }

    public Notice( String subject, String body) {
        this.Receiver = null;
        this.Subject = subject;
        this.Body = body;
    }
    // created getter and setter methods
    // for all our variables.
    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getSubject()
    {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getBody()
    {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }
}
