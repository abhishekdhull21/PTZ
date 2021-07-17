package com.aimers.zone.Modals;

public class Notification {
    String header,body;

    public Notification(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
