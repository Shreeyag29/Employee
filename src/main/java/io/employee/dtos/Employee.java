package io.employee.dtos;

import java.util.List;

import io.github.employee.db.ActivityEntity;
import lombok.Data;

@Data
public class Employee {
	
	Long employee_id;

	List<Activity> activities;
}
