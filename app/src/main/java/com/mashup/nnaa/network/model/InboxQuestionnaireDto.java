package com.mashup.nnaa.network.model;

import com.google.gson.JsonObject;

public class InboxQuestionnaireDto {
    public String category;
    public String createdAt;
    public String id;
    public int questionsCount;
    public String senderId;
    public String senderName;
    public String questionnairesId;
    public String sender;
    public JsonObject answers;
    public JsonObject questions;

    /**
     * Create questionnaire item
     * @param questionnairesId
     * @param sender
     */
    public InboxQuestionnaireDto(String questionnairesId,
                                      String sender) {
        this.questionnairesId = questionnairesId;
        this.sender = sender;
    }

    /**
     * Create "No Items" item
     */
    public InboxQuestionnaireDto() {
        this.questionnairesId = "";
        this.sender = "No items";
    }
}
