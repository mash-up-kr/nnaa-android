package com.mashup.nnaa.data;

public class DeleteQuestionItem {

    String checkBox;
    String question_Txt;

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public String getQuestion_Txt() {
        return question_Txt;
    }

    public void setQuestion_Txt(String question_Txt) {
        this.question_Txt = question_Txt;
    }

    public DeleteQuestionItem() {
        this.checkBox = checkBox;
        this.question_Txt = question_Txt;
    }
}