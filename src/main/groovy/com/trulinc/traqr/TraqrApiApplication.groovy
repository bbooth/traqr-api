package com.trulinc.traqr

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TraqrApiApplication {

    static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TraqrApiApplication, args)))
    }
}
