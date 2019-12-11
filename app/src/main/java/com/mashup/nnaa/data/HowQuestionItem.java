package com.mashup.nnaa.data;

import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;

public class HowQuestionItem {

    String questionary2;
    int imgbtn;
    int viewType;

    public String getQuestionary2() {
        return questionary2;
    }

    public void setQuestionary2(String questionary2) {
        this.questionary2 = questionary2;
    }

    public int getImgbtn() {
        return imgbtn;
    }

    public void setImgbtn(int imgbtn) {
        this.imgbtn = imgbtn;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public HowQuestionItem(String questionary2, int imgbtn, int viewType) {
        this.questionary2 = questionary2;
        this.imgbtn = imgbtn;
        this.viewType = viewType;
    }
}
