package com.iteesoft.drone.enums;

public enum ModelType {
    LIGHT_WEIGHT("LIGHTWEIGHT"),
    MIDDLE_WEIGHT("MIDDLEWEIGHT"),
    CRUISER_WEIGHT("CRUISERWEIGHT"),
    HEAVY_WEIGHT("HEAVYWEIGHT");

    public final String label;

    ModelType(String label){
        this.label = label;
    }
}
