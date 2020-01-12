package io.github.employee.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "employee")
@Getter
@Setter
@ToString(exclude = "activities")
@EqualsAndHashCode(exclude = "activities")
public class EmployeeEntity {
	
	@Id
	private Long id;
	
	@OneToMany(targetEntity=ActivityEntity.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)  
    private List<ActivityEntity> activities;  
}
