package com.mashup.nnaa.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questions {

    @SerializedName("additionalProp")
    @Expose
    private AdditionalProp additionalProp;

    public AdditionalProp getAdditionalProp() {
        return additionalProp;
    }

    public void setAdditionalProp(AdditionalProp additionalProp) {
        this.additionalProp = additionalProp;
    }
}
