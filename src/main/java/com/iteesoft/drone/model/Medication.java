package com.iteesoft.drone.model;

import lombok.*;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medication extends Base {
    private String name;
    private Integer weight;
    private String code;
    private String imageUrl;
}
