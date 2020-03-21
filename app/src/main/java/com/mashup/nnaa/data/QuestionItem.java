package com.mashup.nnaa.data;

import java.io.Serializable;

public class QuestionItem implements Serializable {
    String qeustion_num;
    String question_content;

    public QuestionItem() {

    }

    public String getQeustion_num() {
        return qeustion_num;
    }

    public void setQeustion_num(String qeustion_num) {
        this.qeustion_num = qeustion_num;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public QuestionItem(String qeustion_num, String question_content) {
        this.qeustion_num = qeustion_num;
        this.question_content = question_content;
    }
}
