package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.data.JobRepo;
import com.mycompany.climbworksscheduler.data.OffDayRepo;
import com.mycompany.climbworksscheduler.data.RatingRepo;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Job;
import com.mycompany.climbworksscheduler.entity.OffDay;
import com.mycompany.climbworksscheduler.entity.Rating;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Guides {

    @Autowired
    EmployeeRepo empRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    OffDayRepo odRepo;

    @Autowired
    RatingRepo ratingRepo;

    public List<Employee> getGuides(LocalDate elDia) {

        List<Employee> guides = empRepo.findAll().stream()
                .map(e -> remap(e))
                .collect(Collectors.toList());

        guides = removeByOffDay(guides, elDia);

        List<Employee> receivers = getReceivers(guides);

        for (Employee receiver : receivers) {
            guides.remove(receiver);
        }

        List<Employee> possibleSenders = getPossibleSenders(guides);

        List<Employee> senders = getSenders(receivers, possibleSenders);

        List<Employee> gottenGuides = new ArrayList<>();

        receivers.forEach(r -> gottenGuides.add(r));

        senders.forEach(s -> gottenGuides.add(s));

        return gottenGuides;

    }

    private List<Employee> getReceivers(List<Employee> emps) {

        List<Employee> receiverList = emps.stream()
                .filter(e -> e.getJobList().stream()
                .anyMatch(j -> j.getJobId() == 2))
                .collect(Collectors.toList());

        List<Employee> receivers = new ArrayList<>();

        Random r = new Random();

        for (int i = 0; i < 8; i++) {
            int a = r.nextInt(receiverList.size());
            receivers.add(receiverList.get(a));
            receiverList.remove(a);
        }

        return receivers;

    }

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

    private List<Employee> getSenders(List<Employee> receivers, List<Employee> possibleSenders) {

        Random r = new Random();

        List<Employee> actualSenders = new ArrayList<>();

        for (Employee receiver : receivers) {
            List<Employee> senderList = new ArrayList<>();
            int recId = receiver.getEmployeeId();
            for (Employee possibleSender : possibleSenders) {
                int rate = 0, rate2 = 0;
                Rating rating = ratingRepo.findByRatedEmployeeIdAndRaterId(possibleSender.getEmployeeId(), recId);
                Rating rating2 = ratingRepo.findByRatedEmployeeIdAndRaterId(recId, possibleSender.getEmployeeId());
                if (rating == null) {
                    rate = 2;
                } else {
                    rate = rating.getRate();
                }
                if (rating2 == null) {
                    rate2 = 2;
                } else {
                    rate2 = rating2.getRate();
                }
                int theRate = rate + rate2;
                for (int i = 0; i < theRate; i++) {
                    senderList.add(possibleSender);
                }
            }
            int randomNum = r.nextInt(senderList.size());
            Employee sender = senderList.get(randomNum);
            actualSenders.add(sender);
            possibleSenders.remove(sender);
        }
        
        return actualSenders;

    }
    
    public List<Employee> getAvailableReceivers(LocalDate elDia) {
        List<Employee> guides = empRepo.findAll().stream()
                .map(e -> remap(e))
                .filter(e -> e.getJobList().stream()
                .anyMatch(j -> j.getJobId() == 2))
                .collect(Collectors.toList());

        return removeByOffDay(guides, elDia);
        
    }
    
    public List<Employee> getAvailableSenders(LocalDate elDia) {
        List<Employee> guides = empRepo.findAll().stream()
                .map(e -> remap(e))
                .filter(e -> e.getJobList().stream()
                .anyMatch(j -> j.getJobId() == 1))
                .collect(Collectors.toList());

        return removeByOffDay(guides, elDia);
        
    }

    private List<Employee> removeByOffDay(List<Employee> empList, LocalDate theDate) {

        List<OffDay> offList = odRepo.findByOffDate(theDate).stream()
                .filter(od -> od.isIsResolved() == true)
                .collect(Collectors.toList());

        for (OffDay od : offList) {
            empList = empList.stream()
                    .filter(e -> e.getEmployeeId() != od.getEmployeeId())
                    .collect(Collectors.toList());
        }
        return empList;
    }

    private List<Employee> getPossibleSenders(List<Employee> guides) {

        return guides.stream()
                .filter(g -> g.getJobList().stream()
                .anyMatch(j -> j.getJobId() == 1))
                .collect(Collectors.toList());
    }

    public List<Employee> getOffEmployees(LocalDate elDia) {

        List<Employee> guides = empRepo.findAll().stream()
                .map(e -> remap(e))
                .collect(Collectors.toList());
        
        List<OffDay> offList = odRepo.findByOffDate(elDia).stream()
                .filter(od -> od.isIsResolved() == true)
                .collect(Collectors.toList());
        
        if (offList.size() == 0) {
            List<Employee> none = new ArrayList<>();
            return none;
        }
        
        List<Integer> guideIds = new ArrayList<>();
        
        for (OffDay od : offList) {
            guideIds.add(od.getEmployeeId());
        }
        
        List<Employee> offGuides = new ArrayList<>();
        
        for (int id : guideIds) {
            offGuides.add(empRepo.findById(id).orElse(null));
        }
        
        offGuides = offGuides.stream()
                .map(g -> remap(g))
                .collect(Collectors.toList());
        
        return offGuides;
        
    }
}