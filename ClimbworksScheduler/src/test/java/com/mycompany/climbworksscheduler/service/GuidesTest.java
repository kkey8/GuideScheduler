package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.entity.OffDay;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GuidesTest {

    @Autowired
    Guides gs;

    @Autowired
    OffDayRepo odRepo;

    public GuidesTest() {
    }

    @Before
    public void setUp() {

        odRepo.deleteAll();

    }

    /**
     * Test of getGuides method, of class Guides.
     */
    @Test
    public void testGetGuides() {

        LocalDate theDay = LocalDate.of(2019, 5, 10);

        // 16 guides per DaySchedule
        assertTrue(gs.getGuides(theDay).size() == 16);

    }

    /**
     * Test of getAvailableReceivers method, of class Guides.
     */
    @Test
    public void testGetAvailableReceivers() {

        LocalDate theDay = LocalDate.of(2019, 5, 10);

        assertTrue(gs.getAvailableReceivers(theDay).size() == 15);

        OffDay od = new OffDay();
        od.setEmployeeId(1);
        od.setIsResolved(true);
        od.setOffDate(theDay);
        OffDay od2 = new OffDay();
        od2.setEmployeeId(2);
        od2.setIsResolved(true);
        od2.setOffDate(theDay);
        odRepo.save(od);
        odRepo.save(od2);

        assertTrue(gs.getAvailableReceivers(theDay).size() == 13);

    }

    /**
     * Test of getAvailableSenders method, of class Guides.
     */
    @Test
    public void testGetAvailableSenders() {

        LocalDate theDay = LocalDate.of(2019, 5, 10);

        assertTrue(gs.getAvailableSenders(theDay).size() == 15);

        OffDay od = new OffDay();
        od.setEmployeeId(30);
        od.setIsResolved(true);
        od.setOffDate(theDay);
        OffDay od2 = new OffDay();
        od2.setEmployeeId(28);
        od2.setIsResolved(true);
        od2.setOffDate(theDay);
        odRepo.save(od);
        odRepo.save(od2);

        assertTrue(gs.getAvailableSenders(theDay).size() == 13);

    }

    /**
     * Test of getOffEmployees method, of class Guides.
     */
    @Test
    public void testGetOffEmployees() {

        LocalDate theDay = LocalDate.of(2019, 5, 10);

        assertTrue(gs.getOffEmployees(theDay).size() == 0);
        
        OffDay od = new OffDay();
        od.setEmployeeId(30);
        od.setIsResolved(true);
        od.setOffDate(theDay);
        OffDay od2 = new OffDay();
        od2.setEmployeeId(22);
        od2.setIsResolved(true);
        od2.setOffDate(theDay);
        odRepo.save(od);
        odRepo.save(od2);
        
        Integer offEmps = gs.getOffEmployees(theDay).size();
        
        assertTrue(offEmps == 2);
        
    }

}
