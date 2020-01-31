package com.mashup.nnaa.data;

import android.widget.EditText;

public class CustomQuestionItem {

    private String txt_question;
    private String editText;
    private int custom_img;
    private int viewType;

    public CustomQuestionItem(String txt_question, String editText, int custom_img, int viewType) {
        this.txt_question = txt_question;
        this.editText = editText;
        this.custom_img = custom_img;
        this.viewType = viewType;
    }

    public String getTxt_question() {
        return txt_question;
    }

    public void setTxt_question(String txt_question) {
        this.txt_question = txt_question;
    }

    public String getEditText() {
        return editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public int getCustom_img() {
        return custom_img;
    }

    public void setCustom_img(int custom_img) {
        this.custom_img = custom_img;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
