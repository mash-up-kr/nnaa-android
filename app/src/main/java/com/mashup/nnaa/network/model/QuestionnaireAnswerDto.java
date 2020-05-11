package com.mashup.nnaa.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class QuestionnaireAnswerDto {
    @SerializedName("answers")
    @Expose
    private HashMap<String, String> answers;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public QuestionnaireAnswerDto(HashMap<String, String> answers, String updatedAt) {
        this.answers = answers;
        this.updatedAt = updatedAt;
    }

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}