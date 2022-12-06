package com.example.attendancesystem;

public class AttendanceHelper {
    String status,date;
    public AttendanceHelper(){

    }
    public AttendanceHelper( String status, String date) {
        this.date = date;
        this.status = status;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
