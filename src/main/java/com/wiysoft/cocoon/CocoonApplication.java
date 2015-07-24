package com.wiysoft.cocoon;


import com.wiysoft.cocoon.core.model.Slot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;

/**
 * Created by weiliyang on 7/24/15.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {Slot.class})
public class CocoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoonApplication.class, args);
    }
}
