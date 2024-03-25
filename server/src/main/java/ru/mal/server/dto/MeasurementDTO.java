package ru.mal.server.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.mal.server.models.Sensor;

public class MeasurementDTO {

    @NotNull
    @Min(value = -100,message = "temperature should be more than -100")
    @Max(value = 100,message = "temperature should be less than 100")
    private Double temperature;

    @NotNull
    private Boolean raining;

    @NotNull
    private Sensor sensor;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
