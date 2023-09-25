package com.example.bids.VO;

import com.example.bids.entity.Ads;
import com.example.bids.entity.Bids;

public class AdDetail {

    private Ads ad;
    private Car car;
    private Bids bids;



    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }

    public Ads getAd() {
        return ad;
    }

    public void setAd(Ads ad) {
        this.ad = ad;
    }
}
