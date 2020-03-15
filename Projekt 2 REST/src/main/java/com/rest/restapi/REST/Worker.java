package com.rest.restapi.REST;

import com.rest.restapi.entity.data.Data;
import com.rest.restapi.entity.data.Values;
import com.rest.restapi.entity.sensor.Sensor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Worker extends Thread {
    int id;
    Buffer buffer;
    String param;

    public Worker(int id, Buffer buffer, String param){
        this.id =id;
        this.buffer = buffer;
        this.param = param;
    }

    public void run(){
        RestTemplate restTemplate = new RestTemplate();
        Sensor[] sensors = restTemplate.getForObject("http://api.gios.gov.pl/pjp-api/rest/station/sensors/"+id, Sensor[].class);
       // System.out.println(sensors);
        for (Sensor j:sensors) {
           // System.out.println(j.getParam().getParamCode() + " "+param);
            if (j.getParam().getParamCode().equals(param)) {
                synchronized (buffer){
                    buffer.add(getData(j.getId()));
                }
            }
        }
    }
    private double getData(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Data data = restTemplate.getForObject("http://api.gios.gov.pl/pjp-api/rest/data/getData/"+id,Data.class);
        List<Values> val = data.getValues();
        return val.get(val.size()-1).getValue();

    }
}
