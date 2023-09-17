package com.iteesoft.drone.controller;

import com.iteesoft.drone.dto.DroneDto;
import com.iteesoft.drone.dto.Response;
import com.iteesoft.drone.model.Drone;
import com.iteesoft.drone.model.Medication;
import com.iteesoft.drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/drones")
@RequiredArgsConstructor
public class DispatchController {

    private final DroneService droneService;

    @GetMapping("/")
    public ResponseEntity<?> viewAll() {
        return ResponseEntity.ok(droneService.viewAllDrones());
    }

    @PostMapping("/register")
    public ResponseEntity<Drone> registerDrone(@RequestBody DroneDto droneInfo) {
        return new ResponseEntity<>(droneService.register(droneInfo), HttpStatus.CREATED);
    }

    @PostMapping("/{droneId}/load/{medicId}")
    public ResponseEntity<Drone> loadDrone(@PathVariable UUID droneId, @PathVariable UUID medicId) {
        return new ResponseEntity<>(droneService.loadWithMedication(droneId, medicId), OK);
    }

    @GetMapping("/id/{droneId}")
    public ResponseEntity<Drone> viewInfo(@PathVariable UUID droneId) {
        return new ResponseEntity<>(droneService.getDroneById(droneId), OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<Drone> viewBySerialNumber(@PathVariable String serialNumber) {
        return new ResponseEntity<>(droneService.getDrone(serialNumber), OK);
    }

    @GetMapping("/viewLoad/{droneId}")
    public ResponseEntity<Response> viewLoadedMedication(@PathVariable UUID droneId) {
        return ResponseEntity.ok(droneService.viewDroneItems(droneId));
    }

    @GetMapping("/viewAvailable")
    public ResponseEntity<List<Drone>> viewAvailableDrone() {
        return new ResponseEntity<>(droneService.viewAvailableDrone(), OK);
    }

    @GetMapping("/viewBattery/{droneId}")
    public ResponseEntity<String> viewBattery(@PathVariable UUID droneId) {
        return new ResponseEntity<>(droneService.viewDroneBattery(droneId), OK);
    }

    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> viewAllMedications() {
        return new ResponseEntity<>(droneService.viewAllMedications(), OK);
    }
}
