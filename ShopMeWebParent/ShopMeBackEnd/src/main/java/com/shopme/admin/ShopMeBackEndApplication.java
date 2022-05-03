package com.shopme.admin;

import com.shopme.admin.user.UserRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity", "com.shopme.admin.user"})
public class ShopMeBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMeBackEndApplication.class, args);
    }

}
