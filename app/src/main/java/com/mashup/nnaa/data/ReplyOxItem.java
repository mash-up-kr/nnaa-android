package com.mashup.nnaa.data;

import android.graphics.drawable.Drawable;

public class ReplyOxItem {

    private int image_o;
    private int imag_x;

    public ReplyOxItem() {
        
    }

    public int getImage_o() {
        return image_o;
    }

    public void setImage_o(int image_o) {
        this.image_o = image_o;
    }

    public int getImag_x() {
        return imag_x;
    }

    public void setImag_x(int imag_x) {
        this.imag_x = imag_x;
    }

    public ReplyOxItem(int image_o, int imag_x) {
        this.image_o = image_o;
        this.imag_x = imag_x;
    }

    public void setImage_o(Drawable drawable) {
    }

    public void setImag_x(Drawable drawable) {
    }
}
