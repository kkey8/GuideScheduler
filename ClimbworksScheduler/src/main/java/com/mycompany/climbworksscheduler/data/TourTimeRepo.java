package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.TourTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourTimeRepo extends JpaRepository<TourTime, Integer> {

}
