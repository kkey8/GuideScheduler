package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.DaySchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayScheduleRepo extends JpaRepository<DaySchedule, Integer> {
    
    public List<DaySchedule> findByCourseId(int courseId);

    public DaySchedule findByCourseIdAndTheDate(int courseId, LocalDate theDate);
    
}
