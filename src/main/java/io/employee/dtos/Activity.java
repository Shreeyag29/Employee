package io.employee.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class Activity {
	String name;
	Date time;
	int duration;
}
