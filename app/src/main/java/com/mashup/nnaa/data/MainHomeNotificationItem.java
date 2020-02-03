package com.mashup.nnaa.data;

public class MainHomeNotificationItem {
    private String message;
    private String link;

    public MainHomeNotificationItem(String message, String link) {
        this.message = message;
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public String getLink() {
        return link;
    }
}
