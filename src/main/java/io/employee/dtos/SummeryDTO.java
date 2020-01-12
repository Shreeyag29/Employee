package io.employee.dtos;

import java.util.List;

import io.github.employee.db.ActivityEntity;
import io.github.employee.db.EmployeeEntity;
import lombok.Data;

@Data
public class SummeryDTO {
	
	private List<ActivitySummeryDTO> all_employees_last_7_days_statistics;
private List<EmployeeActivitiesDTO> todays_activities;
}
