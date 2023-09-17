package com.iteesoft.drone.dto;

import com.iteesoft.drone.enums.ModelType;
import lombok.Data;

@Data
public class DroneDto {
    private String serialNumber;
    private ModelType model;
    private Integer weightLimit;
    private int batteryCapacity;
}
