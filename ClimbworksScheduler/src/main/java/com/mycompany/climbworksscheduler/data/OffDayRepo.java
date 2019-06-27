package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.OffDay;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffDayRepo extends JpaRepository<OffDay, Integer> {

    public List<OffDay> findByEmployeeId(int employeeId);
    
    public List<OffDay> findByOffDate(LocalDate offDate);
}
