package com.iug.jerusalem_city.models;

public class Intro {

    private String titleImage;
    private String titleText;

    private String subTitleImage;
    private String subTitleText;

    public Intro() {
    }

    public Intro(String titleImage, String titleText, String subTitleImage, String subTitleText) {
        this.titleImage = titleImage;
        this.titleText = titleText;
        this.subTitleImage = subTitleImage;
        this.subTitleText = subTitleText;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getSubTitleImage() {
        return subTitleImage;
    }

    public void setSubTitleImage(String subTitleImage) {
        this.subTitleImage = subTitleImage;
    }

    public String getSubTitleText() {
        return subTitleText;
    }

    public void setSubTitleText(String subTitleText) {
        this.subTitleText = subTitleText;
    }
}
