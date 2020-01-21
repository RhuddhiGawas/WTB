package com.example.myapplication.Helping_Class;

public class User {
    String name;
    String mail;
    String phone;
    String pass;
    String add;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getAlRlogin() {
        return alRlogin;
    }

    public void setAlRlogin(String alRlogin) {
        this.alRlogin = alRlogin;
    }

    public User(String name, String mail, String phone, String pass, String add ) {
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.pass = pass;
        this.add = add;

    }

    String alRlogin;

}