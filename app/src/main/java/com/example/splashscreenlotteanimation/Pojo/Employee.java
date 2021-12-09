/*
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

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }
}
*/
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
    public String gender;
    public String dob;

    //creating an empty constructor.
    public Employee() {
    }


    public Employee(String email, String name, String contact_no, String employee_id, String supervisor_id, String address, String gender, String pdf_url, String dob) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.supervisor_id = supervisor_id;
        this.address=address;
        this.gender=gender;
        this.pdf_url=pdf_url;
        this.dob = dob;
    }


    public Employee(String email, String name, String contact_no, String employee_id, String supervisor_id) {
        this.email = email;
        this.name = name;
        this.contact_no = contact_no;
        this.employee_id = employee_id;
        this.supervisor_id = supervisor_id;
        this.address="";
        this.gender="";
        this.pdf_url="";
        this.dob = "";
    }


    protected Employee(Parcel in) {
        email = in.readString();
        name = in.readString();
        contact_no = in.readString();
        employee_id = in.readString();
        supervisor_id = in.readString();
        address = in.readString();
        gender = in.readString();
        pdf_url = in.readString();
        dob = in.readString();
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
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeString(pdf_url);
        parcel.writeString(dob);
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

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
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
}