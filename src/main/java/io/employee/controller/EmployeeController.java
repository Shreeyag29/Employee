package io.employee.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.employee.dtos.ActivitySummeryDTO;
import io.employee.dtos.Employee;
import io.employee.dtos.SummeryDTO;
import io.github.employee.db.EmployeeEntity;
import io.github.employee.service.FileDataFetcherService;

@RestController("/")
public class EmployeeController {
	

	
	@Autowired
	private FileDataFetcherService fileDataFetcherService;
	

	

	
	@GetMapping("/summery")
	public SummeryDTO summery() {
		return fileDataFetcherService.summery();
	}

}
