package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job, Integer> {

    public List<Employee> findByJobId(int jobId);
    
}
