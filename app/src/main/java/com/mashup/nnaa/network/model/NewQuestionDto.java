package com.mashup.nnaa.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashup.nnaa.data.Choices;

import java.io.Serializable;

public class NewQuestionDto implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("choices")
    @Expose
    private Choices choices;
    @SerializedName("bookmarkQuestionId")
    @Expose
    private String bookmarkQuestionId;
    @SerializedName("isBookmarked")
    @Expose
    private boolean isBookmarked;

    public NewQuestionDto(String content, String category, String type, Choices choices) {
        this.content = content;
        this.category = category;
        this.type = type;
        this.choices = choices;
    }

    public NewQuestionDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Choices getChoices() {
        return choices;
    }

    public void setChoices(Choices choices) {
        this.choices = choices;
    }

    public String getBookmarkQuestionId() {
        return bookmarkQuestionId;
    }

    public void setBookmarkQuestionId(String bookmarkQuestionId) {
        this.bookmarkQuestionId = bookmarkQuestionId;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}