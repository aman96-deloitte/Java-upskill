package com.javaupskill.carsnbids.VO;

public class CarFilterBody {

    private String transmission;
    private String bodyType;
    private int year;


    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getBodyType() {
        return bodyType;
    }

    public CarFilterBody() {
        this.transmission = null;
        this.bodyType = null;

    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}