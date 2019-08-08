package com.greenHouse.project;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.greenHouse.entity.Ciudad;

@SpringBootApplication(scanBasePackages={"com.greenHouse"})
@EnableJpaRepositories("com.greenHouse.repository")
@EntityScan(basePackageClasses = Ciudad.class)
public class GreenHouseApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(GreenHouseApplication.class, args);
	}

}
