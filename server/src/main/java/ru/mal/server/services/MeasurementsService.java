package ru.mal.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.server.models.Measurement;
import ru.mal.server.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void add(Measurement measurement){
        enrich(measurement);
        measurementsRepository.save(measurement);
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public void enrich(Measurement measurement){
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setMadeAt(LocalDateTime.now());
    }

}
