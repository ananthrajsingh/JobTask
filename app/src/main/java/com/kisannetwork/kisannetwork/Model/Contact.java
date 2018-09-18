package com.kisannetwork.kisannetwork.Model;

/**
 * Created by ananthrajsingh on 17/09/18
 * Model class of Contact
 */
public class Contact {


    private int age;
    private String name;
    private String gender;
    private String email;
    private String phone;

    public Contact() {
    }

    public Contact(int age, String name, String gender, String email, String phone) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
