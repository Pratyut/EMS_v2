/*
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
}
*/

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
    public String gender;
    public String dob;
    public String supervisor_id;

    public Manager(String email, String name, String employee_id, String contact_no, String address, String gender, String pdf_url, String dob, String supervisor_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.pdf_url = pdf_url;
        this.address = address;
        this.employee_id = employee_id;
        this.gender = gender;
        this.dob = dob;
        this.supervisor_id = "";
    }

    public Manager(String email, String name,  String contact_no,String employee_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.pdf_url = "";
        this.address = "";
        this.gender = "";
        this.dob = "";
        this.supervisor_id = "";
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    protected Manager(Parcel in) {
        email = in.readString();
        name = in.readString();
        employee_id = in.readString();
        contact_no = in.readString();
        address = in.readString();
        gender = in.readString();
        pdf_url = in.readString();
        dob = in.readString();
        supervisor_id = in.readString();
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
        parcel.writeString(employee_id);
        parcel.writeString(contact_no);
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeString(pdf_url);
        parcel.writeString(dob);
        parcel.writeString(supervisor_id);
    }
}
