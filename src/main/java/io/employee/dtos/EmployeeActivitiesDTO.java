package io.employee.dtos;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeActivitiesDTO{
	long employee_id;
	List<ActivityDTO> activities;
}