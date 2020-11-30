package com.management.carrot97.bean;

public class OriginalUser {
    private String username;
    private String password1;
    private String password2;
    private String birthday;
    private Degree degree;
    private String email;
    private String phoneNumber;

    public OriginalUser() {
    }

    public OriginalUser(String username, String password1, String password2, String birthday, Degree degree, String email, String phoneNumber) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.birthday = birthday;
        this.degree = degree;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "OriginalUser{" +
                "username='" + username + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", birthday='" + birthday + '\'' +
                ", degree=" + degree +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
