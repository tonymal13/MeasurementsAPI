package ru.mal.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.server.models.Measurement;


@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement,Integer> {

}
