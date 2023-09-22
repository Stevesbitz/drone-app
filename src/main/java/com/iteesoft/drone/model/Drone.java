package com.iteesoft.drone.model;

import com.iteesoft.drone.enums.ModelType;
import com.iteesoft.drone.enums.State;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Drone extends Base {

    @Size(min = 16, max = 100)
    private String serialNumber;

//    @Enumerated(EnumType.STRING)
    private ModelType model;

    @Max(500)
    private Integer weightLimit;

    private int batteryCapacity;

//    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.IDLE;
}
