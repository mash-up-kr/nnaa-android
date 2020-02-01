package com.mashup.nnaa.data;

public class ContactItem {
    private String name;
    private String number;

    public ContactItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
