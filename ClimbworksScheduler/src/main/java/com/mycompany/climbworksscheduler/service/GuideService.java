package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.DayScheduleRepo;
import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.data.JobRepo;
import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.data.RatingRepo;
import com.mycompany.climbworksscheduler.data.TourRepo;
import com.mycompany.climbworksscheduler.entity.DaySchedule;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Job;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.Rating;
import com.mycompany.climbworksscheduler.entity.Tour;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class GuideService {

    @Autowired
    OffDayRepo odRepo;

    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    EmployeeRepo empRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    DayScheduleRepo dsRepo;

    @Autowired
    TourRepo tourRepo;

    // Blocks
    public ResponseEntity<OffDay> acceptOffRequest(String username, OffDay od) {

        int id = empRepo.findByUsername(username).orElse(null).getEmployeeId();

        LocalDate newDate = od.getOffDate().plusDays(1);
        od.setOffDate(newDate);
        od.setEmployeeId(id);

        odRepo.save(od);

        return new ResponseEntity<>(od, HttpStatus.ACCEPTED);
    }

    // Calendar
    public List<Tour> getTours(DaySchedule ds) {

        List<DaySchedule> theDay = dsRepo.findByCourseId(ds.getCourseId()).stream()
                .filter(s -> s.getTheDate().equals(ds.getTheDate()))
                .collect(Collectors.toList());

        if (theDay.size() == 0) {
            return tourRepo.findByTourTimeId(1);
        }

        int dsId = theDay.get(0).getDayScheduleId();

        return tourRepo.findByDayScheduleId(dsId);
    }

    // Ratings
    public List<Employee> getEmployeesForRating(String username) {

        int id = empRepo.findByUsername(username).orElse(null).getEmployeeId();

        List<Employee> theList = empRepo.findAll().stream()
                .map(e -> remap(e))
                .sorted(Comparator.comparing(Employee::getFirstName))
                .filter(e -> e.getEmployeeId() != id)
                .collect(Collectors.toList());

        return theList;

    }

    public List<Rating> getEmployeeRatings(String username) {

        int id = empRepo.findByUsername(username).orElse(null).getEmployeeId();

        return ratingRepo.findByRaterId(id);

    }

    public ResponseEntity<Rating[]> saveRatings(String username, @RequestBody Rating[] ratings) {

        int id = empRepo.findByUsername(username).orElse(null).getEmployeeId();

        int l = ratings.length;

        for (int i = 0; i < l; i++) {
            Rating r = ratingRepo.findByRatedEmployeeIdAndRaterId(ratings[i].getRatedEmployeeId(), id);

            if (r == null) {
                if (ratings[i].getRate() == 0) {
                    Rating rating = new Rating();
                    rating.setRate(2);
                    rating.setRaterId(id);
                    rating.setRatedEmployeeId(ratings[i].getRatedEmployeeId());
                    ratingRepo.save(rating);
                } else {
                    Rating rating = new Rating();
                    rating.setRate(ratings[i].getRate());
                    rating.setRaterId(id);
                    rating.setRatedEmployeeId(ratings[i].getRatedEmployeeId());
                    ratingRepo.save(rating);
                }
            } else {
                r.setRaterId(id);
                r.setRate(ratings[i].getRate());
                ratingRepo.save(r);
            }
//            ratingRepo.setFixedRateFor(ratings[i].getRate(), ratings[i].getRaterId(), ratings[i].getRatedEmployeeId());
            }

            return new ResponseEntity(ratings, HttpStatus.ACCEPTED);
        }
        // Sign Out
    private Employee remap(Employee e) {
        Employee result = new Employee();
        result.setEmployeeId(e.getEmployeeId());
        result.setFirstName(e.getFirstName());
        result.setLastName(e.getLastName());
        result.setLocationId(e.getLocationId());
        for (Job j : e.getJobList()) {
            result.getJobList().add(remap(j));
        }
        return result;
    }

    private Job remap(Job j) {
        Job result = new Job();
        result.setJobId(j.getJobId());
        result.setTitle(j.getTitle());
        return result;
    }

}
