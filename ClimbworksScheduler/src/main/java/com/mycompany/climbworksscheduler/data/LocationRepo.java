package com.mycompany.climbworksscheduler.data;

import com.mycompany.climbworksscheduler.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

}
