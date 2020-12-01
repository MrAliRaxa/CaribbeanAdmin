package com.e.caribbeanadmin.data_model;

import java.util.ArrayList;
import java.util.List;

public class CountrySlider {

    private List<String> sliderContent;
    private int sliderType;

    public CountrySlider() {

        sliderContent =new ArrayList<>();

    }

    public List<String> getSliderContent() {
        return sliderContent;
    }

    public void setSliderContent(List<String> sliderContent) {
        this.sliderContent = sliderContent;
    }



    public int getSliderType() {
        return sliderType;
    }

    public void setSliderType(int sliderType) {
        this.sliderType = sliderType;
    }
}
