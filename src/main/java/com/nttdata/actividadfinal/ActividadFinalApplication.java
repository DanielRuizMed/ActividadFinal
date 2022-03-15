package com.nttdata.actividadfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ActividadFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActividadFinalApplication.class, args);
	}

}
