package com.mashup.nnaa.network.model;

public class Question {
    public String category;
    public String[] choices;
    public String content;
    public String id;
    public String type;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Question(String category, String[] choices, String content, String id, String type) {
        this.category = category;
        this.choices = choices;
        this.content = content;
        this.id = id;
        this.type = type;
    }
}