package ru.mal.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mal.server.models.Sensor;
import ru.mal.server.services.SensorsService;

@Component
public class SensorValidator implements Validator {

    private SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor =(Sensor) target;

        if (sensorsService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name","","Sensor with this name already exists");

    }
}
