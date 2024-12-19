package org.yearup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

@SpringBootApplication
public class EasyshopApplication
{

    public static void main(String[] args) {
        SpringApplication.run(EasyshopApplication.class, args);
    }

}
