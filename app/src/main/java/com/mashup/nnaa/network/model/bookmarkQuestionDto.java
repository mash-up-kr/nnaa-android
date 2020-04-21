package com.mashup.nnaa.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashup.nnaa.data.Choices;

public class bookmarkQuestionDto {

    @SerializedName("bookmarkQuestionId")
    @Expose
    private String bookmarkQuestionId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("choices")
    @Expose
    private Choices choices;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("type")
    @Expose
    private String type;

    public String getBookmarkQuestionId() {
        return bookmarkQuestionId;
    }

    public void setBookmarkQuestionId(String bookmarkQuestionId) {
        this.bookmarkQuestionId = bookmarkQuestionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Choices getChoices() {
        return choices;
    }

    public void setChoices(Choices choices) {
        this.choices = choices;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}