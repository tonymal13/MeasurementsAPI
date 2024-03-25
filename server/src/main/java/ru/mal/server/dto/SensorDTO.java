package ru.mal.server.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @Column(name = "name")
    @NotEmpty
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
