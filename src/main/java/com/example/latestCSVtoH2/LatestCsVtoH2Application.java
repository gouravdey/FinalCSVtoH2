package com.example.latestCSVtoH2;

import com.example.latestCSVtoH2.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LatestCsVtoH2Application implements CommandLineRunner {

	@Autowired
	EmployeeDAO employeeDAO;

	public static void main(String[] args) {
		SpringApplication.run(LatestCsVtoH2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("List of employees in the database");
		for (Employee employee : employeeDAO.findAll()) {
			System.out.println(employee);
		}
	}
}
