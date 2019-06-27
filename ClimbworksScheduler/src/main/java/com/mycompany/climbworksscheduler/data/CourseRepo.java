package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    
    public List<Course> findByLocationId(int locationId);
}
