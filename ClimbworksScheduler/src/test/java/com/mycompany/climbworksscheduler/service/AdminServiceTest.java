package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.DayScheduleRepo;
import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.data.JobRepo;
import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.data.TourRepo;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.OffDay;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    DayScheduleRepo dsRepo;

    @Autowired
    EmployeeRepo empRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    OffDayRepo odRepo;

    @Autowired
    TourRepo tRepo;

    @Autowired
    Guides guides;

    public AdminServiceTest() {
    }

    @Before
    public void setUp() {

    }

    /**
     * Test of getTourTimes method, of class AdminService.
     */
    @Test
    public void testGetTourTimes() {

        assertTrue(adminService.getTourTimes().size() == 8);

    }

    /**
     * Test of getDayScheduleEmployees method, of class AdminService.
     */
    @Test
    public void testGetDayScheduleEmployeesAndPersistGuides() {

        LocalDate theDate = LocalDate.parse("2019-05-10");
        List<Employee> guidesList = guides.getGuides(theDate);
        int[] empIds = new int[16];
        int i = 0;
        for (Employee guide : guidesList) {
            empIds[i] = guide.getEmployeeId();
            i++;
        }

        adminService.persistGuides(empIds, "2019-05-10");

        assertTrue(adminService.getDayScheduleEmployees("2019-05-10").size() == 16);
        assertTrue(empIds[0] == adminService.getDayScheduleEmployees("2019-05-10").get(0).getEmployeeId());

        tRepo.deleteAll();

    }

    /**
     * Test of getEmployeePage method, of class AdminService.
     */
    @Test
    public void testGetEmployeePage() {

        assertTrue(adminService.getEmployeePage().size() == 30);

    }

    /**
     * Test of add method, of class AdminService.
     */
    @Test
    public void testAddAndDelete() {

        Employee e = new Employee();
        e.setFirstName("Hannah");
        e.setLastName("Banana");
        e.setLocationId(1);
        e.setActive(1);
        e.setPassword("password");
        e.setUsername("username");
        assertTrue(adminService.add(e).getStatusCode() == HttpStatus.CREATED);
        int size = adminService.getEmployeePage().size();
        assertTrue(adminService.getEmployeePage().size() == 31);
        int id = empRepo.findAll().stream()
                .filter(emp -> emp.getLastName().equalsIgnoreCase("banana"))
                .collect(Collectors.toList())
                .get(0)
                .getEmployeeId();
        adminService.deleteEmployee(id);
        assertTrue(adminService.getEmployeePage().size() == 30);

    }

    /**
     * Test of getEmployee method, of class AdminService.
     */
    @Test
    public void testGetEmployee() {

        assertNotNull(adminService.getEmployee(1));

    }

    /**
     * Test of getEmployeeJobs method, of class AdminService.
     */
    @Test
    public void testGetEmployeeJobs() {

        assertTrue(adminService.getEmployeeJobs(1).size() == 1);
        assertTrue(adminService.getEmployeeJobs(20).size() == 1);
        assertTrue(adminService.getEmployeeJobs(10).size() == 1);

    }

    /**
     * Test of updateEmployee method, of class AdminService.
     */
    @Test
    public void testUpdateEmployee() {

        int numJobs = adminService.getEmployeeJobs(1).size();
        int[] newJobs = new int[2];
        newJobs[0] = 1;
        newJobs[1] = 2;
        adminService.updateEmployee(1, newJobs);
        assertFalse(numJobs == adminService.getEmployeeJobs(1).size());
        int[] reversion = new int[1];
        reversion[0] = 2;
        adminService.updateEmployee(1, reversion);
        assertTrue(numJobs == adminService.getEmployeeJobs(numJobs).size());

    }

    /**
     * Test of getOffRequests method, of class AdminService.
     */
    @Test
    public void testGetOffRequests() {

        OffDay od1 = new OffDay();
        od1.setEmployeeId(1);
        od1.setIsResolved(false);
        od1.setOffDate(LocalDate.of(2019, 5, 10));
        OffDay od2 = new OffDay();
        od2.setEmployeeId(2);
        od2.setIsResolved(false);
        od2.setOffDate(LocalDate.of(2019, 5, 8));
        odRepo.save(od1);
        odRepo.save(od2);

        assertTrue(adminService.getOffRequests().size() == 2);

        odRepo.deleteAll();

    }

    /**
     * Test of acceptOffRequest method, of class AdminService.
     */
    @Test
    public void testAcceptOffRequest() {
        
        odRepo.deleteAll();

        OffDay od1 = new OffDay();
        od1.setEmployeeId(1);
        od1.setIsResolved(false);
        od1.setOffDate(LocalDate.of(2019, 5, 10));
        OffDay od2 = new OffDay();
        od2.setEmployeeId(2);
        od2.setIsResolved(false);
        od2.setOffDate(LocalDate.of(2019, 5, 8));
        odRepo.save(od1);
        odRepo.save(od2);
        List<Integer> ids = new ArrayList<>();
        List<OffDay> ods = odRepo.findAll();
        for (OffDay od : ods) {
            ids.add(od.getOffDayId());
        }
        for (int id : ids) {
            adminService.acceptOffRequest(id);
        }
        
        int size = odRepo.findAll().stream()
                .filter(o -> o.isIsResolved() == true)
                .collect(Collectors.toList())
                .size();

        assertTrue(size == 2);

        odRepo.deleteAll();

    }

    /**
     * Test of rejectOffRequest method, of class AdminService.
     */
    @Test
    public void testRejectOffRequest() {

        OffDay od1 = new OffDay();
        od1.setEmployeeId(1);
        od1.setIsResolved(false);
        od1.setOffDate(LocalDate.of(2019, 5, 10));
        OffDay od2 = new OffDay();
        od2.setEmployeeId(2);
        od2.setIsResolved(false);
        od2.setOffDate(LocalDate.of(2019, 5, 8));
        odRepo.save(od1);
        odRepo.save(od2);
        List<Integer> ids = new ArrayList<>();
        List<OffDay> ods = odRepo.findAll();
        for (OffDay od : ods) {
            ids.add(od.getOffDayId());
        }
        for (int id : ids) {
            adminService.rejectOffRequest(id);
        }

        assertTrue(odRepo.findAll().size() == 0);

    }

    /**
     * Test of getGuides method, of class AdminService.
     */
    @Test
    public void testGetGuides() {

        String theDate = "2019-05-10";
        assertTrue(adminService.getGuides(theDate).size() == 16);

    }

    /**
     * Test of getAvailableReceivers method, of class AdminService.
     */
    @Test
    public void testGetAvailableReceivers() {

        odRepo.deleteAll();

        String theDate = "2019-05-10";
        assertTrue(adminService.getAvailableReceivers(theDate).size() == 15);

    }

    /**
     * Test of getAvailableSenders method, of class AdminService.
     */
    @Test
    public void testGetAvailableSenders() {

        odRepo.deleteAll();

        String theDate = "2019-05-15";
        assertTrue(adminService.getAvailableSenders(theDate).size() == 15);

    }

    /**
     * Test of getOffEmployees method, of class AdminService.
     */
    @Test
    public void testGetOffEmployees() {

        String stringDay = "2019-05-10";
        LocalDate theDay = LocalDate.parse(stringDay);

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

        Integer offEmps = adminService.getOffEmployees(stringDay).size();

        assertTrue(offEmps == 2);

    }

}
