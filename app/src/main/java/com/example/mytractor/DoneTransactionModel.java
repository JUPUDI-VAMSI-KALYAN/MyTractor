package com.example.mytractor;

public class DoneTransactionModel {

    public DoneTransactionModel(){
    }

    public DoneTransactionModel(String item_id,String name, String phone, String hours, String minutes, String total_amount, String paid_amount, String not_paid, String partially_paid, String fully_paid) {
        this.name = name;
        this.phone = phone;
        this.hours = hours;
        this.minutes = minutes;
        this.total_amount = total_amount;
        this.paid_amount = paid_amount;
        this.not_paid = not_paid;
        this.partially_paid = partially_paid;
        this.fully_paid = fully_paid;
        this.item_id = item_id;
    }

    private String name;
    private String phone;
    private String hours;
    private String minutes;
    private String total_amount;
    private String paid_amount;
    private String not_paid,partially_paid,fully_paid;
    private String item_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getNot_paid() {
        return not_paid;
    }

    public void setNot_paid(String not_paid) {
        this.not_paid = not_paid;
    }

    public String getPartially_paid() {
        return partially_paid;
    }

    public void setPartially_paid(String partially_paid) {
        this.partially_paid = partially_paid;
    }

    public String getFully_paid() {
        return fully_paid;
    }

    public void setFully_paid(String fully_paid) {
        this.fully_paid = fully_paid;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}
