package com.mycompany.climbworksscheduler.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tour {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int tourId;
    
    private int tourTimeId;
    
    private int dayScheduleId;
    
    private int senderId;

    private int receiverOneId;

    private Integer receiverTwoId;

    public int getTourTimeId() {
        return tourTimeId;
    }

    public void setTourTimeId(int tourTimeId) {
        this.tourTimeId = tourTimeId;
    }

    public int getDayScheduleId() {
        return dayScheduleId;
    }

    public void setDayScheduleId(int dayScheduleId) {
        this.dayScheduleId = dayScheduleId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverOneId() {
        return receiverOneId;
    }

    public void setReceiverOneId(int receiverOneId) {
        this.receiverOneId = receiverOneId;
    }

    public Integer getReceiverTwoId() {
        return receiverTwoId;
    }

    public void setReceiverTwoId(Integer receiverTwoId) {
        this.receiverTwoId = receiverTwoId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
    
}
