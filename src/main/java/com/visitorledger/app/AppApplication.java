package com.visitorledger.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.repository.EmployeeRepository;
import com.visitorledger.app.repository.VisitorRepository;

@SpringBootApplication
//@EnableScheduling  uncomment in personal laptop
public class AppApplication {
    
    
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		
	}

}
