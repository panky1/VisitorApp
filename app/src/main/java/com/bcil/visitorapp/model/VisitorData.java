package com.bcil.visitorapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VisitorData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "companyname")
    private String companyname;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "designation")
    private String designation;

    @ColumnInfo(name = "loc")
    private String loc;

    @ColumnInfo(name = "emailid")
    private String emailid;

    @ColumnInfo(name = "mobileno")
    private String mobileno;

    @ColumnInfo(name = "solutions")
    private String solutions;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "attendby")
    private String attendby;

    public VisitorData(String companyname, String name, String designation, String loc, String emailid, String mobileno, String solutions, String time,String attendby) {
        this.companyname = companyname;
        this.name = name;
        this.designation = designation;
        this.loc = loc;
        this.emailid = emailid;
        this.mobileno = mobileno;
        this.solutions = solutions;
        this.time = time;
        this.attendby = attendby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSolutions() {
        return solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAttendby() {
        return attendby;
    }

    public void setAttendby(String attendby) {
        this.attendby = attendby;
    }
}
