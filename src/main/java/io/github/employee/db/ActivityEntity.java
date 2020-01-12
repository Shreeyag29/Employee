package io.github.employee.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "activity")
@Data
public class ActivityEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
    private String name;

	@Column(name = "time")
    private Date time;
	
	@Column(name = "duration")
	private long duration;
	
	@JsonIgnore
	@ManyToOne(targetEntity = EmployeeEntity.class)
	private EmployeeEntity employee;


}
