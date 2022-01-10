package com.example.mytractor;

public class ContactModel {

    public ContactModel(){}

    private String contactname;
    private String contactphone;

    public ContactModel(String name, String phone) {
        this.contactname = name;
        this.contactphone = phone;
    }

    public String getName() {
        return contactname;
    }

    public void setName(String name) {
        this.contactname = name;
    }

    public String getPhone() {
        return contactphone;
    }

    public void setPhone(String phone) {
        this.contactphone = phone;
    }
}
