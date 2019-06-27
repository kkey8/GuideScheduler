package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.entity.Employee;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Validations {

    public Validations() {
    }

    public List<String> validateEmployee(Employee e) {

        List<String> errMsg = new ArrayList<>();

        if (e.getFirstName().length() > 25 || e.getFirstName().trim().length() == 0) {
            errMsg.add("First name must be 25 characters or less and more than 0 characters.");
        }

        if (e.getLastName().length() > 25 || e.getLastName().trim().length() == 0) {
            errMsg.add("Last name must be 25 characters or less and more than 0 characters.");
        }

        if (!e.getFirstName().matches("[a-zA-Z][a-zA-Z]*")) {
            errMsg.add("First name must only contain letters A-Z, in both lowercase and uppercase.");
        }

        if (!e.getLastName().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
            errMsg.add("Last name must only contain letters A-Z, in both lowercase and uppercase.");
        }
        
        return errMsg;
    }
    
    public List<String> validateDate(LocalDate elDia) {
        
        List<String> errorMessages = new ArrayList<>();
        
        if (elDia.getDayOfWeek() == DayOfWeek.SUNDAY) {
            errorMessages.add("This day is a Sunday. We are not open on Sundays.");
        }
        
        return errorMessages;
        
    }

    boolean dateIsInPast(LocalDate elDia) {

        return elDia.isBefore(LocalDate.now()) || elDia.isEqual(LocalDate.now());
        
    }

}
