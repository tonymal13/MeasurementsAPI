package ru.mal.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.server.models.Sensor;
import ru.mal.server.repositories.SensorsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundPerson = sensorsRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void register (Sensor sensor){
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        return sensorsRepository.findByName(name);
    }

}
