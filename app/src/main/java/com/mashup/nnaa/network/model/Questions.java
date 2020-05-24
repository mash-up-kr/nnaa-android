package com.mashup.nnaa.network.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Questions {

    private AdditionalProp additionalProp;

    public AdditionalProp getAdditionalProp() {
        return additionalProp;
    }

    public void setAdditionalProp(AdditionalProp additionalProp) {
        this.additionalProp = additionalProp;
    }
}
