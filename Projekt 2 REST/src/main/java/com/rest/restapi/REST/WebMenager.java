package com.rest.restapi.REST;

import com.rest.restapi.entity.station.Station;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WebMenager {

   private Station[] stations;

    public  WebMenager() throws ExecutionException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        stations = restTemplate.getForObject("http://api.gios.gov.pl/pjp-api/rest/station/findAll", Station[].class);
    }

    public String getMean (String city, String param) throws InterruptedException {
        Buffer buffer = new Buffer();
        List<Worker> workers = new LinkedList<>();
        for (Station i:stations)
            if( i.getCity().getName().equals(city) ){
                Worker w = new Worker(i.getId(),buffer,param);
                w.start();
                workers.add(w);
                }
        for (Worker i: workers ) {
            i.join();
        }
        return generateHTMLResponse(city,param,buffer.getSum()/buffer.getN());
    }
    private String generateHTMLResponse (String city,String param,Double mean){
        return "<HTML>" +
                "<head><meta charset=\"UTF-8\"> <title>Breathe as long as you can</title></head>"+
                "<body> <H1>Średnia dla:</H1>" +
                "Miasto: "+city+
                "<br> Parametr: "+param+
                "<br> Mean: "+mean+
                "<br> <a href=\"http://localhost:8080\">BACK</a>"+
                "</body></html>";
    }



}
