package com.mashup.nnaa.network.model;

public class FriendDto {
    String category;
    String name;
    String email;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FriendDto(String category, String name, String email) {
        this.category = category;
        this.name = name;
        this.email = email;
    }
}
