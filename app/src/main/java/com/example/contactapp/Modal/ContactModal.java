package com.example.contactapp.Modal;

public class ContactModal {

    public String Id;
    public String body;
    public String Message;
    public String Date;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
