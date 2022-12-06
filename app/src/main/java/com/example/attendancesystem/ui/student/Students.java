package com.example.attendancesystem.ui.student;

public class Students {
    private String name;
    private String regno;
    private String branch;
    private String email;
    public Students(String name, String regno, String branch, String email)
    {
        this.name=name;
        this.regno=regno;
        this.branch=branch;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
