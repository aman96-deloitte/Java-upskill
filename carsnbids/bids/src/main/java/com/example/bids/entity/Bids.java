package com.example.bids.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Bids {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bidId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private int bidPrice;
    private int bidStatus;

    private int adId;
    private String email;
    private int totalBids;

    public Bids() {
        this.creationDate = new Date() ;
        this.bidStatus = 0;
        this.totalBids = 0;
    }


    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }


    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(int bidStatus) {
        this.bidStatus = bidStatus;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
