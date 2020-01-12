package io.github.employee.db;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDAO extends JpaRepository<ActivityEntity, Long> {

	
	List<ActivityEntity>  findAllByTimeAfter(Date date);
}
