package com.mycompany.climbworksscheduler.controller;

import com.mycompany.climbworksscheduler.entity.DaySchedule;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.Rating;
import com.mycompany.climbworksscheduler.entity.Tour;
import com.mycompany.climbworksscheduler.service.GuideService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GuideController {
    
    @Autowired
    GuideService gService;

    // Blocks
    @PostMapping("/submitOffRequest")
    public ResponseEntity<OffDay> acceptOffRequest(@RequestBody OffDay od) {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        return gService.acceptOffRequest(username, od);
    }

    // Calendar
    @PostMapping("/getDaySchedule")
    public List<Tour> getTours(@RequestBody DaySchedule ds) {
        return gService.getTours(ds);
    }
    
    // Ratings
    @GetMapping("/getEmployees")
    public List<Employee> getEmployeesForRating() {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        return gService.getEmployeesForRating(username);
    }

    @GetMapping("/getRatings")
    public List<Rating> getEmployeeRatings() {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        return gService.getEmployeeRatings(username);
    }

    @PostMapping("/saveRatings")
    public ResponseEntity<Rating[]> saveRatings(@RequestBody Rating[] ratings) {
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        return gService.saveRatings(username, ratings);
    }
}