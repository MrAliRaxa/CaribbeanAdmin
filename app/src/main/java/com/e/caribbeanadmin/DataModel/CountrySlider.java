package com.e.caribbeanadmin.DataModel;

import java.util.ArrayList;
import java.util.List;

public class CountrySlider {

    private List<String> images;
    private String videos;
    private int sliderType;

    public CountrySlider() {

        images=new ArrayList<>();

    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public int getSliderType() {
        return sliderType;
    }

    public void setSliderType(int sliderType) {
        this.sliderType = sliderType;
    }
}
