package tu.practice.mobile_system.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.repository.AdministratorRepository;
import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.repository.AdministratorRepository;

@Service
public class AdministratorService {
	@Autowired
    private AdministratorRepository repository;
	
	public List<Administrator> getAllAdmins() {
		List<Administrator> admins = repository.findAll();
		return admins;
	}
	/*
	//change status method//
	public List<Course> changeCourseStatus(Long courseId) {
	List<Course> courses = repository.findAll();
	List<Course> resultList = new ArrayList<Course>();
		for(int coursesIterator = 0; coursesIterator < courses.size(); coursesIterator++) {
			if(courses.get(coursesIterator).getCourseId()== courseId) {
				if(courses.get(coursesIterator).getCourseStatus().equals("Inactive")) {
				courses.get(coursesIterator).setCourseStatus("Active");
				}
				else {
					if(courses.get(coursesIterator).getCourseStatus().equals("Active")) {
					courses.get(coursesIterator).setCourseStatus("Inactive");
				}
					else {
						//no change?? invalid status
					}
				resultList.add(courses.get(coursesIterator));
			}
		}
		
	}
		return resultList;
}	 
	public List<Course> getAllActiveCourses() {
		List<Course> courses = repository.findAll();
		List<Course> result = new ArrayList<Course>();
		for(int coursesIterator = 0; coursesIterator < courses.size(); coursesIterator++) {
			if(courses.get(coursesIterator).getCourseStatus().equals("Active")) {
				result.add(courses.get(coursesIterator));
			}
		}
		return result;
	}
	
	public List<Course> getAllInactiveCourses() {
		List<Course> courses = repository.findAll();
		List<Course> result = new ArrayList<Course>();
		for(int coursesIterator = 0; coursesIterator < courses.size(); coursesIterator++) {
			if(courses.get(coursesIterator).getCourseStatus().equals("Inactive")) {
				result.add(courses.get(coursesIterator));
			}
		}
		return result;
	}
	
	public void saveCoursesStatus(Course course) {
		repository.saveAndFlush(course);
		return;
	}
	*/
}
