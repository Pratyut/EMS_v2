package com.example.splashscreenlotteanimation.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Manager implements Parcelable {
    public String email;
    public String name;
    public String contact_no;
    public String pdf_url;
    public String address;
    public String employee_id;


    public Manager(String email, String name, String contact_no, String employee_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.address="";
        this.pdf_url="";
    }

    public Manager(String email, String name, String contact_no, String pdf_url, String address, String employee_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.pdf_url = pdf_url;
        this.address = address;
        this.employee_id = employee_id;
    }

    protected Manager(Parcel in) {
        email = in.readString();
        name = in.readString();
        contact_no = in.readString();
        pdf_url = in.readString();
        address = in.readString();
        employee_id = in.readString();
    }

    public static final Creator<Manager> CREATOR = new Creator<Manager>() {
        @Override
        public Manager createFromParcel(Parcel in) {
            return new Manager(in);
        }

        @Override
        public Manager[] newArray(int size) {
            return new Manager[size];
        }
    };

    public Manager() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(contact_no);
        parcel.writeString(pdf_url);
        parcel.writeString(address);
        parcel.writeString(employee_id);
    }
}
