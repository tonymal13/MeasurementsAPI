package ru.mal.server.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mal.server.dto.MeasurementDTO;
import ru.mal.server.dto.MeasurementsResponse;
import ru.mal.server.models.Measurement;
import ru.mal.server.services.MeasurementsService;
import ru.mal.server.util.MeasurementErrorResponse;
import ru.mal.server.util.MeasurementException;
import ru.mal.server.util.MeasurementValidator;

import java.util.stream.Collectors;

import static ru.mal.server.util.Errors.sendErrorsToClient;


@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;

    private final MeasurementValidator measurementValidator;

    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }



    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors())
            sendErrorsToClient(bindingResult);

        measurementsService.add(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCost")
    public long getRainyDaysCount(){
        return (measurementsService.findAll()).stream().filter(measurement -> (!measurement.isRaining())).count();
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO,Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDTO.class);
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
