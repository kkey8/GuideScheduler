package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.EmployeeRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleRepo extends JpaRepository<EmployeeRole, Integer> {

    public List<EmployeeRole> findByEmployeeId(int employeeId);
    
}
