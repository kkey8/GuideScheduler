package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    
    public List<Employee> findByLocationId(int locationId);
    
    Optional<Employee> findByUsername(String username);
    
    public void deleteByUsername(String username);
    
}
