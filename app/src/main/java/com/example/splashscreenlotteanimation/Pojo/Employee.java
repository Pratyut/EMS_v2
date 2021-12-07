package com.example.splashscreenlotteanimation.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
    public String email;
    public String name;
    public String contact_no;
    public String pdf_url;
    public String address;
    public String employee_id;
    public String supervisor_id;

    //creating an empty constructor.
    public Employee() {

    }


    public Employee(String email, String name, String contact_no, String employee_id, String supervisor_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.supervisor_id = supervisor_id;
        this.address="";
        this.pdf_url="";

    }

    public Employee(String email, String name, String contact_no, String employee_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.supervisor_id=null;
        this.address="";
        this.pdf_url="";


    }


    protected Employee(Parcel in) {
        email = in.readString();
        name = in.readString();
        contact_no = in.readString();
        employee_id = in.readString();
        supervisor_id = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(contact_no);
        parcel.writeString(employee_id);
        parcel.writeString(supervisor_id);
    }
}
