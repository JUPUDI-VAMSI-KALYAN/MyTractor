package com.example.mytractor;

public class DriverModel {

    private String amount;
    private String timestamp;
    private String id;

    public DriverModel(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DriverModel(String amount, String timestamp, String id){
        this.amount = amount;
        this.timestamp=timestamp;
        this.id= id;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
