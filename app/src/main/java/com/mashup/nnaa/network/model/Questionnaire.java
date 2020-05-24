package com.mashup.nnaa.network.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

public class Questionnaire implements Serializable {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("questions")
    @Expose
    private JsonObject questions;

    public JsonObject getQuestions() {
        return questions;
    }

    public void setQuestions(JsonObject questions) {
        this.questions = questions;
    }

    @SerializedName("receiverId")
    @Expose
    private String receiverId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public Questionnaire(String category, String createdAt, JsonObject questions, String receiverId) {
        this.category = category;
        this.createdAt = createdAt;
        this.questions = questions;
        this.receiverId = receiverId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

}