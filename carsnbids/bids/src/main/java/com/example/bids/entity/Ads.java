package com.example.bids.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int aid;
    private String email;
    private float currentAmount;
    private long carId;
    private int adStatus;

    private LocalDateTime bidEndTime;

    private LocalDateTime creationDate;

    public Ads() {
        this.creationDate = LocalDateTime.now(); // Initialize the creation date to the current date/time.
        this.currentAmount = 0;
        this.adStatus = 1;
    }

    public LocalDateTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalDateTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public int getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(int adStatus) {
        this.adStatus = adStatus;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
