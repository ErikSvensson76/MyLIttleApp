package org.example.mylittleapp;

import org.example.mylittleapp.config.BaseUrl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
    value = BaseUrl.class
)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
