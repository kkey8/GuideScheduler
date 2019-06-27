package com.mycompany.climbworksscheduler.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DaySchedule {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int dayScheduleId;
    
    private LocalDate theDate;
    
    private int courseId;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getDayScheduleId() {
        return dayScheduleId;
    }

    public void setDayScheduleId(int dayScheduleId) {
        this.dayScheduleId = dayScheduleId;
    }

    public LocalDate getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDate theDate) {
        this.theDate = theDate;
    }

}
