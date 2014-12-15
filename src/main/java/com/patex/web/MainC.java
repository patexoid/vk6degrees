package com.patex.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apotekhin on 12/10/2014.
 */
@RestController
public class MainC {

    @RequestMapping("/")
    String home() {
        return "redirect:http://localhost:8080/auth/";
    }
}
