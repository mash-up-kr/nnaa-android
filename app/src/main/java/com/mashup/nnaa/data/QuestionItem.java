package com.mashup.nnaa.data;

import java.io.Serializable;

public class QuestionItem implements Serializable {
    String qeustion_num;
    String question_content;
    int question_img;
    int viewType;

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

    public int getQuestion_img() {
        return question_img;
    }

    public void setQuestion_img(int question_img) {
        this.question_img = question_img;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public QuestionItem(String qeustion_num, String question_content, int question_img, int viewType) {
        this.qeustion_num = qeustion_num;
        this.question_content = question_content;
        this.question_img = question_img;
        this.viewType = viewType;
    }
}
