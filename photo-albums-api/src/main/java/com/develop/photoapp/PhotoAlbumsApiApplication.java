package com.develop.photoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAlbumsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhotoAlbumsApiApplication.class, args);
	}
}