package com.mashup.nnaa.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionnaireAnswerDto {
    @SerializedName("answers")
    @Expose
    private Answers answers;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public QuestionnaireAnswerDto(Answers answers, String updatedAt) {
        this.answers = answers;
        this.updatedAt = updatedAt;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
