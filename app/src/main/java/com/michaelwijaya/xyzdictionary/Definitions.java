package com.michaelwijaya.xyzdictionary;

import com.google.gson.annotations.SerializedName;

public class Definitions {
    @SerializedName("image_url")
    private String imageUrl;
    private String type;
    private String definition;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
