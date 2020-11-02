package com.e.caribbeanadmin.DataModel;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private CountryInformation information;
    private CountrySlider countrySlider;
    private String history;
    private Delicacies delicacies;
    private ReligionAndCulture religionAndCulture;
    private String countryId;
    private String flagImageUrl;
    private String armFlagUrl;

    public Country() {
        information=new CountryInformation();
        countrySlider =new CountrySlider();
        delicacies=new Delicacies();
        religionAndCulture=new ReligionAndCulture();
    }


    public ReligionAndCulture getReligionAndCulture() {
        return religionAndCulture;
    }


    public String getArmFlagUrl() {
        return armFlagUrl;
    }

    public void setArmFlagUrl(String armFlagUrl) {
        this.armFlagUrl = armFlagUrl;
    }

    public String getFlagImageUrl() {
        return flagImageUrl;
    }

    public void setFlagImageUrl(String flagImageUrl) {
        this.flagImageUrl = flagImageUrl;
    }

    public CountryInformation getInformation() {
        return information;
    }

    public CountrySlider getCountrySlider() {
        return countrySlider;
    }

    public Delicacies getDelicacies() {
        return delicacies;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
