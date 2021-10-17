package com.security.exercise.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/api/v1/status")
    public String getStatus() {
        return "Hello";
    }


    @GetMapping("/restricted/basic")
    public String getRestricted(){
        return "Secret key";
    }
}
