package com.iteesoft.drone.repository;

import com.iteesoft.drone.model.Drone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DroneRepository extends MongoRepository<Drone, UUID> {

    Optional<Drone> findBySerialNumber(String serialNumber);

//    @Query(value = "select * from drone where state = 'IDLE'", nativeQuery = true)
    @Query("from Drone d where d.state='IDLE'")
    List<Drone> findAvailableDrones();

    boolean existsBySerialNumber(String serialNumber);
}