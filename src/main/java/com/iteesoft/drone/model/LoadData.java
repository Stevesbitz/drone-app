package com.iteesoft.drone.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Document
public class LoadData {
    private UUID droneId;
    private List<LoadInfo> items = new ArrayList<>();

    @Data
    public static class LoadInfo {
        private UUID medicationId;
        private int quantity;
        private boolean delivered;
    }
}
