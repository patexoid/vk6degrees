package com.patex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by apotekhin on 12/10/2014.
 */

@Configuration
@EnableAutoConfiguration
@EnableCaching
@ComponentScan
public class Main {



    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);

    }


}
