package com.rest.restapi.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
public class WebController {
    WebMenager webMenager;

    @Autowired
    public WebController(WebMenager webMenager) {
        this.webMenager = webMenager;
    }

    @GetMapping(value = "/mean")
    String cityAvc(@RequestParam String city, String param){
        try {
            System.out.println(city+" "+ param);
            return webMenager.getMean(city, param);
        }
        catch (Exception e){
            return "Brak odpowiedzi z api";
        }
    }
}
