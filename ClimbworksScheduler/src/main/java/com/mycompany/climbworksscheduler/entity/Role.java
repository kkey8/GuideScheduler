package com.mycompany.climbworksscheduler.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int roleId;
    
    private String roleName;
    
    @ManyToMany(mappedBy = "roles")
    private List<Employee> roleList = new ArrayList<>();

    public List<Employee> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Employee> roleList) {
        this.roleList = roleList;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
