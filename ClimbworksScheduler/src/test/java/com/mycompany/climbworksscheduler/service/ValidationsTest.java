package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.entity.Employee;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidationsTest {
    
    @Autowired
    EmployeeRepo empRepo;
    
    public ValidationsTest() {
    }

    /**
     * Test of validateEmployee method, of class Validations.
     */
    @Test
    public void testValidateEmployee() {
        
        Employee e = empRepo.findById(1).orElse(null);
        
        Validations v = new Validations();
        
        e.setFirstName("aflaksdjflkasjdflkjasdlfkjalskdfjlaskdjflksjdf");
        
        assertTrue(v.validateEmployee(e).size() == 1);
        
        e.setLastName("saldkfj;laskjdf;lkajsdlfkjasldkjf;alskjdf;lkjasd;lfjkals;kdjflakjsdfjjla;sdf");
        
        assertTrue(v.validateEmployee(e).size() == 3);
        
    }

    /**
     * Test of validateDate method, of class Validations.
     */
    @Test
    public void testValidateDate() {
        
        Validations v = new Validations();
        
        assertTrue(v.validateDate(LocalDate.of(2019, 5, 5)).size() == 1);
        
        assertTrue(v.validateDate(LocalDate.of(2019, 5, 6)).size() == 0);
        
    }

    /**
     * Test of dateIsInPast method, of class Validations.
     */
    @Test
    public void testDateIsInPast() {
        
        Validations v = new Validations();
        
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);
        
        assertTrue(v.dateIsInPast(today));
        assertTrue(v.dateIsInPast(yesterday));
        assertFalse(v.dateIsInPast(tomorrow));
        
    }
    
}
