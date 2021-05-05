package com.iug.jerusalem_city.models;

import java.util.List;

public class InformationData {

    private String text;

    private List<String> images;

    public InformationData() {
    }

    public InformationData(String text, List<String> images) {
        this.text = text;
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
