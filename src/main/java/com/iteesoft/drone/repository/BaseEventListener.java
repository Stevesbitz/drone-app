package com.iteesoft.drone.repository;

import com.iteesoft.drone.model.Base;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class BaseEventListener extends AbstractMongoEventListener<Base> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Base> event) {

        super.onBeforeConvert(event);
        Base entity = event.getSource();

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
    }
}
