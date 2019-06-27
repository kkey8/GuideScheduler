package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Tour;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepo extends JpaRepository<Tour, Integer> {

    public List<Tour> findByDayScheduleId(int dayScheduleId);
    
    public List<Tour> findByTourTimeId(int tourTimeid);
    
}
