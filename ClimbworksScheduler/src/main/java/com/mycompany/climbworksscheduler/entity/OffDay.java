package com.mycompany.climbworksscheduler.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OffDay {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int offDayId;

    private int employeeId;

    private LocalDate offDate;
    
    private boolean isResolved;

    public boolean isIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public int getOffDayId() {
        return offDayId;
    }

    public void setOffDayId(int offDayId) {
        this.offDayId = offDayId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getOffDate() {
        return offDate;
    }

    public void setOffDate(LocalDate offDate) {
        this.offDate = offDate;
    }

}
