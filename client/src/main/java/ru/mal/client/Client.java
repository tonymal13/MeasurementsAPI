package ru.mal.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Client {



    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8082/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequest(url, jsonData);
    }

    private static void addMeasurement(double temperature,boolean raining,String sensorName){
        final String url = "http://localhost:8082/measurements/add";

        Map<String,Object> jsonData=new HashMap<>();

        Map<String,Object> sensorData=new HashMap<>();

        sensorData.put("name",sensorName);

        jsonData.put("temperature",temperature);
        jsonData.put("raining",raining);
        jsonData.put("sensor",sensorData);

        makePostRequest(url,jsonData);
    }

    private static void makePostRequest(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        final String sensorName = "Sensor123";
        double temperature=42;
        boolean raining=false;

//        registerSensor(sensorName);
        addMeasurement(temperature,raining,sensorName);

    }
}
