package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Rating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {
    
    public List<Rating> findByRaterId(int raterId);
    
//    @Modifying
//    @Query("update rating r set r.rate = ?1 where r.raterid = ?2 and r.ratedEmployeeId = ?3")
//    int setFixedRateFor(int rate, int raterId, int ratedEmployeeId);

    public Rating findByRatedEmployeeIdAndRaterId(int ratedEmployeeId, int raterId);

    public List<Rating> findByRatedEmployeeId(int ratedEmployeeId);

}
