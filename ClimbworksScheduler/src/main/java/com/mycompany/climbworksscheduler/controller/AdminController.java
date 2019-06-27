package com.mycompany.climbworksscheduler.controller;

import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Job;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.TourTime;
import com.mycompany.climbworksscheduler.service.AdminService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    // Calendar
    @GetMapping("/getTimes")
    public List<TourTime> getTourTimes() {
        return adminService.getTourTimes();
    }

    @PostMapping("/getDaySchedule/{theDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getDayScheduleEmployees(@PathVariable String theDate) {
        return adminService.getDayScheduleEmployees(theDate);
    }

    // Employees
    @GetMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getEmployeePage() {
        return adminService.getEmployeePage();
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> add(@RequestBody Employee e) {
        return adminService.add(e);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return adminService.getEmployee(id);
    }

    @GetMapping("/employeejobs/{id}")
    public List<Job> getEmployeeJobs(@PathVariable int id) {
        return adminService.getEmployeeJobs(id);
    }

    @PostMapping("/employeejobs/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody int[] jobIds) {

        return adminService.updateEmployee(id, jobIds);
    }

    @PostMapping("/deleteemployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        return adminService.deleteEmployee(id);
    }

    // Off Requests
    @GetMapping("/offrequests")
    public List<OffDay> getOffRequests() {
        return adminService.getOffRequests();
    }

    @PostMapping("/acceptrequest/{id}")
    public ResponseEntity<String> acceptOffRequest(@PathVariable int id) {
        return adminService.acceptOffRequest(id);
    }

    @PostMapping("/rejectrequest/{id}")
    public ResponseEntity<String> rejectOffRequest(@PathVariable int id) {
        return adminService.rejectOffRequest(id);
    }

    // Week View
    @GetMapping("/getWeeklyOffCount/{elDia}")
    public List<Integer> getWeeklyOffCount(@PathVariable String elDia) {
        return adminService.getWeeklyOffCount(elDia);
    }
    
    @GetMapping("/getWeeklyGuideCount/{elDia}")
    public List<Integer> getWeeklyGuideCount(@PathVariable String elDia) {
        return adminService.getWeeklyGuideCount(elDia);
    }
    
    // Generate Schedule
    @GetMapping("/getGuides/{theDate}")
    public List<Employee> getGuides(@PathVariable String theDate) {
        return adminService.getGuides(theDate);
    }

    @PostMapping("/persistGuides/{date}")
    public void persistGuides(@RequestBody int[] empIds, @PathVariable String date) {
        adminService.persistGuides(empIds, date);
        return;
    }

    @GetMapping("/getAvailableReceivers/{theDate}")
    public List<Employee> getAvailableReceivers(@PathVariable String theDate) {
        return adminService.getAvailableReceivers(theDate);
    }

    @GetMapping("/getAvailableSenders/{theDate}")
    public List<Employee> getAvailableSenders(@PathVariable String theDate) {
        return adminService.getAvailableSenders(theDate);
    }
    
    @GetMapping("/getOffEmployees/{theDate}")
    public List<Employee> getOffEmployees(@PathVariable String theDate) {
        return adminService.getOffEmployees(theDate);
    }
}
