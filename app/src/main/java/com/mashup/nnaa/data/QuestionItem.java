package com.mashup.nnaa.data;

public class QuestionItem {

    String questionary;
    String mainQ;

    public String getQuestionary() {
        return questionary;
    }

    public void setQuestionary(String questionary) {
        this.questionary = questionary;
    }

    public String getMainQ() {
        return mainQ;
    }

    public void setMainQ(String mainQ) {
        this.mainQ = mainQ;
    }

    public QuestionItem() {
        this.questionary = questionary;
        this.mainQ = mainQ;
    }
}
