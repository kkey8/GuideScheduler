package com.mycompany.climbworksscheduler.entity;

import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TourTime {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int tourTimeId;
    
    private LocalTime theTime;

    public int getTourTimeId() {
        return tourTimeId;
    }

    public void setTourTimeId(int tourTimeId) {
        this.tourTimeId = tourTimeId;
    }

    public LocalTime getTheTime() {
        return theTime;
    }

    public void setTheTime(LocalTime theTime) {
        this.theTime = theTime;
    }

}
