package com.iteesoft.drone.service;

import com.iteesoft.drone.dto.DroneDto;
import com.iteesoft.drone.dto.Response;
import com.iteesoft.drone.model.Drone;
import com.iteesoft.drone.model.LoadData;
import com.iteesoft.drone.model.Medication;

import java.util.List;
import java.util.UUID;

public interface DroneService {

    Drone getDroneById(UUID droneId);
    Drone register(DroneDto droneInfo);

    Drone getDrone(String serialNumber);

    Drone loadWithMedication(UUID droneId, UUID medicationId);
    Response viewDroneItems(UUID droneId);
    List<Drone> viewAvailableDrone();
    String viewDroneBattery(UUID droneId);
    List<Medication> viewAllMedications();
    List<Drone> viewAllDrones();
}
