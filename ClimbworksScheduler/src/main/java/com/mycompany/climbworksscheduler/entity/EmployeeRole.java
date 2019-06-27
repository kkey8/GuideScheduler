package com.mycompany.climbworksscheduler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeRole {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int employeeRoleId;
    
    private int employeeId;
    
    private int roleId;

    public int getEmployeeRoleId() {
        return employeeRoleId;
    }

    public void setEmployeeRoleId(int employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
