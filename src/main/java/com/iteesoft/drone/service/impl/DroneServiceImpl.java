package com.iteesoft.drone.service.impl;

import com.iteesoft.drone.dto.DroneDto;
import com.iteesoft.drone.dto.Response;
import com.iteesoft.drone.enums.State;
import com.iteesoft.drone.exceptions.AppException;
import com.iteesoft.drone.exceptions.ResourceNotFoundException;
import com.iteesoft.drone.model.Drone;
import com.iteesoft.drone.model.LoadData;
import com.iteesoft.drone.model.Medication;
import com.iteesoft.drone.repository.DroneRepository;
import com.iteesoft.drone.repository.LoadDataRepository;
import com.iteesoft.drone.repository.MedicationRepository;
import com.iteesoft.drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final LoadDataRepository loadRepository;
    static final String NOT_FOUND = "%s not found";
    static final String DRONE = "Drone";

    @Override
    public Drone register(DroneDto droneInfo) {
        log.info("Registering Drone with s/n: {}", droneInfo.getSerialNumber());

        if (droneRepository.existsBySerialNumber(droneInfo.getSerialNumber())) {
            throw new AppException("Drone with serial number exist");
        }

        Drone drone = Drone.builder()
                .serialNumber(droneInfo.getSerialNumber())
                .model(droneInfo.getModel())
                .batteryCapacity(droneInfo.getBatteryCapacity())
                .weightLimit(droneInfo.getWeightLimit())
                .build();
        return droneRepository.save(drone);
    }

    @Override
    public Drone getDroneById(UUID droneId) {
        var drone = droneRepository.findById(droneId).orElseThrow(()-> new ResourceNotFoundException(String.format(NOT_FOUND, DRONE)));
        log.info("Fetching Drone with id: {} and s/n: {}", droneId, drone.getSerialNumber());
        return drone;
    }

    @Override
    @Cacheable(value="drones", key="#serialNumber")
    public Drone getDrone(String serialNumber) {
        var drone = droneRepository.findBySerialNumber(serialNumber).orElseThrow(()-> new ResourceNotFoundException(String.format(NOT_FOUND, DRONE)));
        log.info("Fetching Drone with s/n: {}", drone.getSerialNumber());
        return drone;
    }

    public void decreaseBatteryLevel(UUID droneId) {
        final int DECREMENT_VALUE = 5;
        var drone = getDroneById(droneId);
        final int newBatteryLevel = drone.getBatteryCapacity() - DECREMENT_VALUE;
        drone.setBatteryCapacity(newBatteryLevel);
        droneRepository.save(drone);
        log.info("Drone s/n: {} new battery level, {}%", drone.getSerialNumber(), newBatteryLevel);
    }

    public Medication getMedication(UUID medicId) {
        var medic = medicationRepository.findById(medicId).orElseThrow(()-> new ResourceNotFoundException("Medication not found"));
        log.info("Fetching Medication with id: {} and name: {}", medicId, medic.getName());
        return medic;
    }

    @Override
    public Drone loadWithMedication(UUID droneId, UUID medicationId) {
        var drone = getDroneById(droneId);
        var medication = getMedication(medicationId);
        final int totalWeight = medication.getWeight() + totalLoadWeight(droneId);
        drone.setState(State.LOADING);
        log.info("Medication with code: {} is been loaded on Drone s/n: {}", medication.getCode(), drone.getSerialNumber());

        if (totalWeight <= drone.getWeightLimit() && drone.getBatteryCapacity() > 25) {

            drone.setState(State.LOADED);
            decreaseBatteryLevel(droneId);
            droneRepository.save(drone);
            log.info("Total load on Drone s/n: {}, {}Kg", drone.getSerialNumber(), totalWeight);
        } else {
            throw new ResourceNotFoundException("Error loading drone, weight limit exceeded");
        }
        return drone;
    }

    @Override
    public Response viewDroneItems(UUID droneId) {
        List<LoadData> loadData = loadRepository.findAllByDroneId(droneId);
        return Response.builder().success(true).data(loadData).build();
    }

    private List<LoadData.LoadInfo> getUndeliveredItems(UUID droneId) {
        List<LoadData> loadData = loadRepository.findAllByDroneId(droneId);
        List<LoadData.LoadInfo> loadInfo = new ArrayList<>();
        if (!loadData.isEmpty()) {
            loadData.forEach(ld -> ld.getItems().stream().filter(med -> !med.isDelivered()).map(loadInfo::add).close());
        }

        return loadInfo;
    }

    @Override
    public List<Drone> viewAvailableDrone() {
        log.info("Fetching all drones in idle state... ");
        return droneRepository.findAvailableDrones();
    }

    @Override
    public String viewDroneBattery(UUID droneId) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(()-> new ResourceNotFoundException(String.format(NOT_FOUND, DRONE)));
        var batteryCapacity = drone.getBatteryCapacity();
        log.info("Drone s/n: {}, Battery level: {}%", drone.getSerialNumber(), batteryCapacity);
        return batteryCapacity+"%";
    }

    @Override
    public List<Medication> viewAllMedications() {
        log.info("Fetching all registered Medications... ");
        return medicationRepository.findAll();
    }

    @Cacheable("drones")
    @Override
    public Response viewAllDrones() {
        log.info("Fetching all registered Drones... ");
        List<Drone> drones = droneRepository.findAll();
        return Response.builder().success(true)
                .data(Map.of("data", drones)).build();
    }

    @Scheduled(initialDelay = 40000, fixedRate = 500000) // cron = "0 1 1 * * ?"
    public void viewAllDroneBattery() {
        log.info("Checking drones Battery level...");
        List<Drone> drones = droneRepository.findAll();
        for (Drone d: drones) {
            log.info("Drone s/n: "+d.getSerialNumber()+", Battery level: "+d.getBatteryCapacity()+"%, Status: "+ d.getState());
        }
    }

    private int totalLoadWeight(UUID droneId) {
        List<LoadData.LoadInfo> undelivered = getUndeliveredItems(droneId);
        List<Medication> med = new ArrayList<>();
        undelivered.forEach(m -> {
            Optional<Medication> medication = medicationRepository.findById(m.getMedicationId());
            medication.ifPresent(med::add);
        });
        return med.stream().map(Medication::getWeight).reduce(0, (a, b) -> a);
    }
}
