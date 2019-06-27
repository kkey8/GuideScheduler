package com.mycompany.climbworksscheduler.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "employeeId")
public class Employee {
    
    public Employee() {
    }

    public Employee(Employee e) {
        this.employeeId = e.getEmployeeId();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.locationId = e.getLocationId();
        this.username = e.getUsername();
        this.password = e.getPassword();
        this.active = e.getActive();
        this.roles = e.getRoles();
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int employeeId;

    @Column(nullable = false)
    @NotBlank(message = "First name is required.")
    @Size(max = 25)
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required.")
    @Size(max = 25)
    private String lastName;

    private int locationId;
    
    @Column(nullable = false)
    @NotBlank(message = "Username is required.")
    @Size(max = 25)
    private String username;
    
    @Column(nullable = false)
    @NotBlank(message = "Password is required.")
    @Size(max = 255)
    private String password;
    
    private int active;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "employeerole", joinColumns = @JoinColumn(name = "employeeid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "employeejob", joinColumns = @JoinColumn(name = "employeeid"), inverseJoinColumns = @JoinColumn(name = "jobid"))
    private List<Job> jobList = new ArrayList<>();

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
