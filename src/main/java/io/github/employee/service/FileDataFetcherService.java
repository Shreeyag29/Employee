package io.github.employee.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.employee.dtos.*;
import io.github.employee.db.ActivityDAO;
import io.github.employee.db.ActivityEntity;
import io.github.employee.db.EmployeeDao;
import io.github.employee.db.EmployeeEntity;

@Service
public class FileDataFetcherService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private ActivityDAO activityDAO;

	@EventListener(ApplicationReadyEvent.class)
	public List<EmployeeEntity> fetchData() {

		ObjectMapper mapper = new ObjectMapper();

		try {
			readDataFromFileAndSaveIntoDB(mapper, "temp.json");
			readDataFromFileAndSaveIntoDB(mapper, "temp2.json");
			return employeeDao.findAll();

		} catch (  IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void readDataFromFileAndSaveIntoDB(ObjectMapper mapper, String fileName)
			throws IOException, JsonParseException, JsonMappingException, FileNotFoundException {
		Employee emp = mapper.readValue(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + fileName), Employee.class);

		EmployeeEntity employeeEntity = mapEmpoloyeeToEmployeeEntity(emp);

		employeeDao.save(employeeEntity);

		System.out.print(employeeDao.findAll());
	}

	public EmployeeEntity mapEmpoloyeeToEmployeeEntity(Employee e) {

		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setId(e.getEmployee_id());

		List<ActivityEntity> activityEntities = new ArrayList<ActivityEntity>();

		for (Activity a : e.getActivities()) {

			ActivityEntity ae = new ActivityEntity();

			ae.setDuration(a.getDuration());

			ae.setEmployee(employeeEntity);

			ae.setName(a.getName());

			ae.setTime(a.getTime());

			activityEntities.add(ae);

		}

		employeeEntity.setActivities(activityEntities);

		return employeeEntity;

	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	public SummeryDTO summery() {

		SummeryDTO summeryDTO = new SummeryDTO();

		setSevenDaysStatistics(summeryDTO);

		setTodaysStats(summeryDTO);
	
		return summeryDTO;

	}

	private void setTodaysStats(SummeryDTO summeryDTO) {
		List<EmployeeEntity>  employees = employeeDao.findAll().stream().map(

				emp -> {

				List<ActivityEntity> activityEntities = 	emp.getActivities().stream().filter(act -> {

						return isSameDay(toCalendar(Calendar.getInstance().getTime()), toCalendar(act.getTime()));
					}).collect(Collectors.toList());
					
				
				emp.setActivities(activityEntities);
					

					return emp;
				}

		).collect(Collectors.toList());
		
		
	
	List<EmployeeActivitiesDTO> employeeActivitiesDTOs = 	employees.stream().map(emp -> {
		
		
		EmployeeActivitiesDTO employeeActivitiesDTO = new EmployeeActivitiesDTO();
		
		employeeActivitiesDTO.setEmployee_id(emp.getId());
		
		List<ActivityDTO> activityDTOs = new ArrayList<ActivityDTO>();
		
		
		emp.getActivities().forEach(act -> {
			
			
			ActivityDTO activityDTO = new ActivityDTO();
			
			activityDTO.setName(act.getName());
			activityDTO.setStart_time(act.getTime().getTime());
			
			activityDTOs.add(activityDTO);
			
		});
		
		
		employeeActivitiesDTO.setActivities(activityDTOs);
		
			return employeeActivitiesDTO;
		}).collect(Collectors.toList());
		

	summeryDTO.setTodays_activities(employeeActivitiesDTOs);
	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private void setSevenDaysStatistics(SummeryDTO summeryDTO) {
		List<ActivitySummeryDTO> allActLast7DaysStats = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date newDate = calendar.getTime();

		List<ActivityEntity> allActivitiesInLastSevenDays = activityDAO.findAllByTimeAfter(newDate);

		Map<String, Integer> map = new HashMap<>();

		allActivitiesInLastSevenDays.forEach(activity ->

		{

			if (map.get(activity.getName()) == null) {
				map.put(activity.getName(), 1);
			} else {

				map.put(activity.getName(), map.get(activity.getName()) + 1);
			}

		});

		map.keySet().forEach(

				val -> {

					ActivitySummeryDTO activitySummeryDTO = new ActivitySummeryDTO();

					activitySummeryDTO.setActivity_name(val);
					activitySummeryDTO.setOccurrences(map.get(val));

					allActLast7DaysStats.add(activitySummeryDTO);

				}

		);

		summeryDTO.setAll_employees_last_7_days_statistics(allActLast7DaysStats);
	}

}
