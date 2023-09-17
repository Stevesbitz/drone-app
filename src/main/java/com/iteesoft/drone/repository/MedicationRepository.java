package com.iteesoft.drone.repository;

import com.iteesoft.drone.model.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicationRepository extends MongoRepository<Medication, UUID> {
}
