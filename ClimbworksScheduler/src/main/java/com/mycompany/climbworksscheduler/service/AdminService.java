package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.DayScheduleRepo;
import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.data.EmployeeRoleRepo;
import com.mycompany.climbworksscheduler.data.JobRepo;
import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.data.RatingRepo;
import com.mycompany.climbworksscheduler.data.RoleRepo;
import com.mycompany.climbworksscheduler.data.TourRepo;
import com.mycompany.climbworksscheduler.data.TourTimeRepo;
import com.mycompany.climbworksscheduler.entity.DaySchedule;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.EmployeeRole;
import com.mycompany.climbworksscheduler.entity.Job;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.Rating;
import com.mycompany.climbworksscheduler.entity.Tour;
import com.mycompany.climbworksscheduler.entity.TourTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdminService {
    
    @Autowired
    EmployeeRoleRepo erRepo;
    
    @Autowired
    EmployeeRepo empRepo;
    
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    OffDayRepo odRepo;

    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    Guides guides;

    @Autowired
    TourTimeRepo tTimeRepo;

    @Autowired
    DayScheduleRepo dsRepo;

    @Autowired
    TourRepo tourRepo;

    // Calendar
    public List<TourTime> getTourTimes() {
        return tTimeRepo.findAll().stream()
                .filter(t -> t.getTheTime().compareTo(LocalTime.of(10, 55)) < 0 && t.getTheTime().compareTo(LocalTime.of(6, 55)) > 0)
                .filter(t -> t.getTheTime().getMinute() == 0 || t.getTheTime().getMinute() == 30)
                .collect(Collectors.toList());
    }

    public List<Employee> getDayScheduleEmployees(@PathVariable String theDate) {

        Validations v = new Validations();
        LocalDate elDia = LocalDate.parse(theDate);
        List<Employee> finalGuides = new ArrayList<>();
        int dsId;
        if (v.validateDate(elDia).size() > 0) {
            return finalGuides;
        }
        if (v.dateIsInPast(elDia)) {
            return finalGuides;
        }
        try {
            dsId = dsRepo.findByCourseIdAndTheDate(1, elDia).getDayScheduleId();
        } catch (Exception e) {
            return finalGuides;
        }
        List<Tour> tours = tourRepo.findByDayScheduleId(dsId);
        List<Employee> guides = new ArrayList<>();
        
        for (Tour t : tours) {
            guides.add(empRepo.findById(t.getReceiverOneId()).orElse(null));
            guides.add(empRepo.findById(t.getSenderId()).orElse(null));
        }
        
//        tours.forEach(t -> guides.add(empRepo.findById(t.getReceiverOneId()).orElse(null)));
//        tours.forEach((t -> guides.add(empRepo.findById(t.getSenderId()).orElse(null))));
        finalGuides = guides.stream()
                .map(e -> remap(e))
                .collect(Collectors.toList());
        return finalGuides;
    }

    // Employees
    public List<Employee> getEmployeePage() {
        return empRepo.findAll().stream()
                .map(e -> remap(e))
                .sorted(Comparator.comparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Employee> add(Employee e) {

        Validations validator = new Validations();

        List<String> errMessages = validator.validateEmployee(e);

        if (errMessages.size() > 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        
        String pw = e.getPassword();
        
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();
        pw = en.encode(pw);
        e.setPassword(pw);
        
        empRepo.save(e);
        
        EmployeeRole er = new EmployeeRole();
        er.setEmployeeId(e.getEmployeeId());
        er.setRoleId(2);
        erRepo.save(er);

        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }

    public Employee getEmployee(@PathVariable int id) {
        Employee e = empRepo.findById(id).orElse(null);
        return e;
    }

    public List<Job> getEmployeeJobs(@PathVariable int id) {
        Employee e = remap(empRepo.findById(id).orElse(null));

        List<Job> jobsList = e.getJobList();

        return jobsList;
    }

    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody int[] jobIds) {

        Employee e = empRepo.findById(id).orElse(null);
        List<Job> jobs = new ArrayList<>();

        for (int jobId : jobIds) {
            jobs.add(jobRepo.findById(jobId).orElse(null));
        }

        e.setJobList(jobs);

        empRepo.save(e);

        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        
        List<Rating> indRatings = ratingRepo.findByRaterId(id);
        if (indRatings != null) {
            for (Rating r : indRatings) {
                ratingRepo.delete(r);
            }
        }

        indRatings = ratingRepo.findByRatedEmployeeId(id);
        if (indRatings != null) {
            for (Rating r : indRatings) {
                ratingRepo.delete(r);
            }
        }
        
        List<Integer> dsIdsToDelete = tourRepo.findAll().stream()
                .filter(t -> t.getReceiverOneId() == id)
                .map(t -> t.getDayScheduleId())
                .collect(Collectors.toList());
        
        List<Integer> dsIdsToDelete2 = tourRepo.findAll().stream()
                .filter(t -> t.getSenderId() == id)
                .map(t -> t.getDayScheduleId())
                .collect(Collectors.toList());
        
        
        for (Integer dsId : dsIdsToDelete2) {
            dsIdsToDelete.add(dsId);
        }
        
        for (Integer dsId : dsIdsToDelete) {
            List<Tour> toursToDelete = tourRepo.findAll().stream()
                    .filter(t -> t.getDayScheduleId() == dsId)
                    .collect(Collectors.toList());
            for (Tour t : toursToDelete) {
                tourRepo.delete(t);
            }
            dsRepo.findById(dsId);
        }
        
        List<EmployeeRole> emproles = erRepo.findByEmployeeId(id);
        
        for (EmployeeRole erole : emproles) {
            erRepo.deleteById(erole.getEmployeeRoleId());
        }
        
        Employee deleteMe = empRepo.findById(id).orElse(null);

        empRepo.delete(deleteMe);

        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    // Off Requests
    public List<OffDay> getOffRequests() {
        return odRepo.findAll().stream()
                .filter(od -> od.isIsResolved() == false)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> acceptOffRequest(@PathVariable int id) {
        OffDay od = odRepo.findById(id).orElse(null);
        od.setIsResolved(true);
        odRepo.save(od);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<String> rejectOffRequest(@PathVariable int id) {
        odRepo.deleteById(id);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Week View
    // Generate Schedule
    public List<Employee> getGuides(String theDate) {
        LocalDate elDia = LocalDate.parse(theDate);
        List<Employee> gList = guides.getGuides(elDia);
        return gList;
    }

    public void persistGuides(@RequestBody int[] empIds, @PathVariable String date) {

        LocalDate elDia = LocalDate.parse(date);

        Validations v = new Validations();

        if (v.dateIsInPast(elDia)) {
            return;
        }

        List<Employee> guides = new LinkedList<>();

        for (int i = 0; i < 16; i++) {
            guides.add(empRepo.findById(empIds[i]).orElse(null));
        }

        if (dsRepo.findByCourseIdAndTheDate(1, elDia) == null) {
            DaySchedule ds = new DaySchedule();
            ds.setCourseId(1);
            ds.setTheDate(elDia);
            dsRepo.save(ds);
        }

        int dsId = dsRepo.findByCourseIdAndTheDate(1, elDia).getDayScheduleId();

        if (tourRepo.findByDayScheduleId(dsId).size() == 0) {
            for (int i = 0; i < 8; i++) {
                Tour t = new Tour();
                t.setDayScheduleId(dsId);
                t.setTourTimeId(i + 1);
                t.setReceiverOneId(empIds[i]);
                t.setSenderId(empIds[i + 8]);
                tourRepo.save(t);
            }
            return;
        }

        int j = 0;
        for (Tour t : tourRepo.findByDayScheduleId(dsId)) {
            t.setSenderId(guides.get(j + 8).getEmployeeId());
            t.setReceiverOneId(guides.get(j).getEmployeeId());
            tourRepo.save(t);
            j++;
        }

        return;

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

    public List<Employee> getAvailableReceivers(String theDate) {

        LocalDate elDia = LocalDate.parse(theDate);

        return guides.getAvailableReceivers(elDia);

    }

    public List<Employee> getAvailableSenders(String theDate) {

        LocalDate elDia = LocalDate.parse(theDate);

        return guides.getAvailableSenders(elDia);

    }

    public List<Employee> getOffEmployees(String theDate) {

        LocalDate elDia = LocalDate.parse(theDate);

        return guides.getOffEmployees(elDia);
    }

    public List<Integer> getWeeklyGuideCount(String theDate) {

        LocalDate elDia = LocalDate.parse(theDate);

        List<LocalDate> weekDays = new ArrayList<>();

        if (elDia.getDayOfWeek() == DayOfWeek.MONDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 6; i++) {
                weekDays.add(elDia.plusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.TUESDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 5; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            weekDays.add(elDia.minusDays(1));

        }

        if (elDia.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 4; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            for (int i = 1; i < 3; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.THURSDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 3; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            for (int i = 1; i < 4; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.FRIDAY) {
            weekDays.add(elDia);
            weekDays.add(elDia.plusDays(1));
            for (int i = 1; i < 5; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.SATURDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 6; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.SUNDAY) {
            for (int i = 1; i < 7; i++) {
                weekDays.add(elDia.plusDays(i));
            }
        }

        List<Integer> idList = new ArrayList<>();
        
        for (LocalDate day : weekDays) {
            String zeDate = day.toString();
            List<Employee> emps = getDayScheduleEmployees(zeDate);
            for (Employee emp : emps) {
                idList.add(emp.getEmployeeId());
            }
        }

        return idList;
    }

    public List<Integer> getWeeklyOffCount(String theDate) {

        LocalDate elDia = LocalDate.parse(theDate);

        List<LocalDate> weekDays = new ArrayList<>();

        if (elDia.getDayOfWeek() == DayOfWeek.MONDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 6; i++) {
                weekDays.add(elDia.plusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.TUESDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 5; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            weekDays.add(elDia.minusDays(1));

        }

        if (elDia.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 4; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            for (int i = 1; i < 3; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.THURSDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 3; i++) {
                weekDays.add(elDia.plusDays(i));
            }
            for (int i = 1; i < 4; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.FRIDAY) {
            weekDays.add(elDia);
            weekDays.add(elDia.plusDays(1));
            for (int i = 1; i < 5; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.SATURDAY) {
            weekDays.add(elDia);
            for (int i = 1; i < 6; i++) {
                weekDays.add(elDia.minusDays(i));
            }
        }

        if (elDia.getDayOfWeek() == DayOfWeek.SUNDAY) {
            for (int i = 1; i < 7; i++) {
                weekDays.add(elDia.plusDays(i));
            }
        }

        List<Integer> idList = new ArrayList<>();
        
        for (LocalDate day : weekDays) {
            for (Employee g : guides.getOffEmployees(day)) {
                idList.add(g.getEmployeeId());
            }
        }

        return idList;
    }

}
