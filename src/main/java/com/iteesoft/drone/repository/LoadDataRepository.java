package com.iteesoft.drone.repository;

import com.iteesoft.drone.model.LoadData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface LoadDataRepository extends MongoRepository<LoadData, UUID> {
    List<LoadData> findAllByDroneId(UUID droneId);
}
