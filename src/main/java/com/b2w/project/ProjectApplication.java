package com.b2w.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.b2w.project.repository.PlanetaRepository;

@SpringBootApplication
public class ProjectApplication  implements CommandLineRunner{
	
	@Autowired
	PlanetaRepository planetaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}
}
