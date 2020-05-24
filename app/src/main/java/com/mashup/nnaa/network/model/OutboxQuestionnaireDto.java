package com.mashup.nnaa.network.model;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

public class OutboxQuestionnaireDto {
    public String category;
    public String createdAt;
    public String id;
    public int questionsCount;
    public String receiverId;
    public String receiverName;
    public JsonObject answers;
    public JsonObject questions;
}
