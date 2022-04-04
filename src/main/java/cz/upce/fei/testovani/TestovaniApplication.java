package cz.upce.fei.testovani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TestovaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestovaniApplication.class, args);
    }

}
/*
semestralka bod 14 integracni a gui
//enable lombok
http://localhost:8080/h2-console
zadat jdbc:h2:mem:f1609ba2-69cc-4253-91a0-22e1865da0ba

prejmenovat na spring.datasource.url=jdbc:h2:mem:testdb
 */
