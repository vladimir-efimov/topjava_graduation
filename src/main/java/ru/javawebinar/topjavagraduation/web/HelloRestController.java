package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = HelloRestController.REST_URL /*, produces = MediaType.APPLICATION_JSON_VALUE*/)
public class HelloRestController {
    public static final String REST_URL = "/rest";

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
