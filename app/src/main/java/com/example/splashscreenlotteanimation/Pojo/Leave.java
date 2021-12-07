package com.example.splashscreenlotteanimation.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Leave implements Parcelable {
    public String userid;
    public String approver_id;
    public String from;
    public String to;
    public String reason;
    public String subject;
    public String status;

    public Leave(){

    }

    public Leave(String userid,String approver_id, String from, String to, String reason, String subject ) {
        this.userid = userid;
        this.approver_id=approver_id;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.subject = subject;
        this.status = "pending";
    }

    protected Leave(Parcel in) {
        userid = in.readString();
        from = in.readString();
        to = in.readString();
        reason = in.readString();
        subject = in.readString();
        status = in.readString();
    }

    public static final Creator<Leave> CREATOR = new Creator<Leave>() {
        @Override
        public Leave createFromParcel(Parcel in) {
            return new Leave(in);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };


    public String getUserid() {
        return userid;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getReason() {
        return reason;
    }

    public String getSubject() {
        return subject;
    }

    public String getStatus() { return  status; }
    public String getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(String approver_id) {
        this.approver_id = approver_id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(approver_id);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(reason);
        dest.writeString(subject);
        dest.writeString(status);
    }
}
