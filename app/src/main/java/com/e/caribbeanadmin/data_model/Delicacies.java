package com.e.caribbeanadmin.data_model;

import java.util.ArrayList;
import java.util.List;

public class Delicacies{

    private List<String> sliderContent;
    private String description;
    private int sliderType;




    public int getSliderType() {
        return sliderType;
    }

    public void setSliderType(int sliderType) {
        this.sliderType = sliderType;
    }

    public Delicacies() {
        sliderContent =new ArrayList<>();
    }

    public List<String> getSliderContent() {
        return sliderContent;
    }

    public void setSliderContent(List<String> sliderContent) {
        this.sliderContent = sliderContent;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}