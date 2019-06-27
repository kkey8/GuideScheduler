package com.mycompany.climbworksscheduler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int ratingId;

    private int raterId;

    private int ratedEmployeeId;

    private int rate;
    
    public int getRaterId() {
        return raterId;
    }

    public void setRaterId(int raterId) {
        this.raterId = raterId;
    }

    public int getRatedEmployeeId() {
        return ratedEmployeeId;
    }

    public void setRatedEmployeeId(int ratedEmployeeId) {
        this.ratedEmployeeId = ratedEmployeeId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
