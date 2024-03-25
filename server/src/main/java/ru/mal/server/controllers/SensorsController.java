package ru.mal.server.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mal.server.dto.SensorDTO;
import ru.mal.server.models.Sensor;
import ru.mal.server.services.SensorsService;
import ru.mal.server.util.MeasurementErrorResponse;
import ru.mal.server.util.MeasurementException;
import ru.mal.server.util.SensorValidator;

import java.io.Serializable;

import static ru.mal.server.util.Errors.sendErrorsToClient;


@RestController
@RequestMapping("/sensors")
public class SensorsController implements Serializable {

    private final SensorsService sensorsService;

    private final SensorValidator sensorValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            sendErrorsToClient(bindingResult);
        }
        sensorsService.register(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
