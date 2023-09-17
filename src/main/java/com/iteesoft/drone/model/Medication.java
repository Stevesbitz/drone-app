package com.iteesoft.drone.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
//import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Medication extends Base {
    private String name;
    private Integer weight;
    private String code;
    private String imageUrl;
}
