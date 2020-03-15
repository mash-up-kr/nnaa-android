package com.mashup.nnaa.data;

import android.widget.Button;
import android.widget.ImageButton;

public class ReplyMultiItem {

    private String multi_txt;
    private int multi_btn;
    private int multi_img;

    public ReplyMultiItem(String multi_txt, int multi_btn, int multi_img) {
        this.multi_txt = multi_txt;
        this.multi_btn = multi_btn;
        this.multi_img = multi_img;
    }

    public ReplyMultiItem() {
        
    }

    public String getMulti_txt() {
        return multi_txt;
    }

    public void setMulti_txt(String multi_txt) {
        this.multi_txt = multi_txt;
    }

    public int getMulti_btn() {
        return multi_btn;
    }

    public void setMulti_btn(int multi_btn) {
        this.multi_btn = multi_btn;
    }

    public int getMulti_img() {
        return multi_img;
    }

    public void setMulti_img(int multi_img) {
        this.multi_img = multi_img;
    }

    public void setMulti_btn(String s) {
    }
}
  
