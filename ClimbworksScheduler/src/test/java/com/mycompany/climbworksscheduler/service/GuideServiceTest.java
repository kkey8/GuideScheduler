package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.DayScheduleRepo;
import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.data.RatingRepo;
import com.mycompany.climbworksscheduler.data.TourRepo;
import com.mycompany.climbworksscheduler.entity.DaySchedule;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.Rating;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GuideServiceTest {

    @Autowired
    AdminService aService;

    @Autowired
    GuideService gService;

    @Autowired
    OffDayRepo odRepo;

    @Autowired
    DayScheduleRepo dsRepo;

    @Autowired
    EmployeeRepo empRepo;

    @Autowired
    TourRepo tRepo;

    @Autowired
    RatingRepo rRepo;

    public GuideServiceTest() {
    }

    @Before
    public void setUp() {

        rRepo.deleteAll();
        tRepo.deleteAll();
        dsRepo.deleteAll();

    }

    /**
     * Test of acceptOffRequest method, of class GuideService.
     */
    @Test
    public void testAcceptOffRequest() {

        odRepo.deleteAll();
        
        OffDay od = new OffDay();
        od.setEmployeeId(1);
        od.setIsResolved(false);
        od.setOffDate(LocalDate.of(2019, 5, 10));

        gService.acceptOffRequest("emp1", od);

        assertTrue(odRepo.findAll().size() == 1);
        assertTrue(odRepo.findAll().stream().filter(o -> o.isIsResolved() == false)
                .collect(Collectors.toList()).size() == 1);

        odRepo.deleteAll();

    }

    /**
     * Test of getTours method, of class GuideService.
     */
    @Test
    public void testGetTours() {

        List<Employee> guides = aService.getGuides("2019-05-10");
        int[] guideIds = new int[16];
        int i = 0;
        for (Employee g : guides) {
            guideIds[i] = g.getEmployeeId();
            i++;
        }
        aService.persistGuides(guideIds, "2019-05-10");
        DaySchedule ds = dsRepo.findByCourseIdAndTheDate(1, LocalDate.of(2019, 5, 10));

        int tourNum = gService.getTours(ds).size();
        assertTrue(tourNum == 8);

    }

    /**
     * Test of getEmployeesForRating method, of class GuideService.
     */
    @Test
    public void testGetEmployeesForRating() {

        Employee e = empRepo.findById(1).orElse(null);

        // 30 employees in database. Should return 29 since rater will not be rating him/herself.
        assertTrue(gService.getEmployeesForRating("emp1").size() == 29);

    }

    /**
     * Test of getEmployeeRatings method, of class GuideService.
     */
    @Test
    public void testGetEmployeeRatings() {
        
        

        assertTrue(gService.getEmployeeRatings("emp1").isEmpty());

        Rating r = new Rating();
        r.setRaterId(1);
        r.setRatedEmployeeId(2);
        r.setRate(1);
        rRepo.save(r);

        assertTrue(gService.getEmployeeRatings("emp1").size() == 1);

        rRepo.deleteAll();

    }

    /**
     * Test of saveRatings method, of class GuideService.
     */
    @Test
    public void testSaveRatings() {

        Rating[] ratings = new Rating[28];

        for (int i = 0; i < ratings.length; i++) {
            Rating r = new Rating();
            r.setRaterId(1);
            r.setRatedEmployeeId(i + 2);
            ratings[i] = r;
        }

        gService.saveRatings("emp1", ratings);

        // No actual rates were set. By default, the rate should be saved as a 2.
        assertTrue(rRepo.findByRaterId(1).get(8).getRate() == 2);
        assertTrue(rRepo.findByRaterId(1).get(12).getRate() == 2);

        rRepo.deleteAll();

    }

}
