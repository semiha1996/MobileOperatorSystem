package tu.practice.mobile_system.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.entity.Customer;
import tu.practice.mobile_system.service.AdministratorService;
import tu.practice.mobile_system.service.CustomerEntityService;
//import tu.practice.mobile_system.entity.Course;
import tu.practice.mobile_system.service.CustomerServiceService;
import tu.practice.mobile_system.service.MobileServiceService;
import tu.practice.mobile_system.service.PayingsService;


@Controller
public class MobileOperatorController {
    @Autowired
    private AdministratorService adminService;
    
    @Autowired
    private CustomerEntityService userService;
    
    @Autowired
    private CustomerServiceService customerServiceService;
    
    @Autowired
    private MobileServiceService mobileServiceService;
    
    @Autowired
    private PayingsService payingsService;
    
    /*
    @GetMapping(value = "/index")
    public String getIndexPage() {
        return "index";
    }
    */
    @GetMapping(value = "/all_admins")
    public String getAllAdminsPage(@RequestParam Optional<String> status, Model model) {
    	List<Administrator> admins = adminService.getAllAdmins();
    	
    	
    	model.addAttribute("admins", admins);
        return "all_courses";
    }
    
    @GetMapping(value = "/all_customers")
    public String getAllCustomersPage(@RequestParam Optional<String> status, Model model) {
    	List<Customer> customers = userService.getAllCustomers();
    	
    	
    	model.addAttribute("customers", customers);
        return "all_courses";
    }
    /*
    //change course status in the menu
    @GetMapping(value = "/change_status")
    public String changeCourseStatusPage(Model model) {
    	model.addAttribute("course", new Course());
    	return "change_status";
     }
    
    @PostMapping("/change_course_status")
    public String foobarPost(@ModelAttribute("course") Course course, Model model ) {
    		System.out.println(course.getCourseId());
    		List<Course> courses = courseService.getAllCourses();
    		for(Course courseRecord : courses) {
    			if(courseRecord.getCourseId() == course.getCourseId()) {
    				if(courseRecord.getCourseStatus().equals("Inactive")) {
    					courseRecord.setCourseStatus("Active");
    				}
    				else {
    					courseRecord.setCourseStatus("Inactive");
    				}
    				
    				courseService.saveCoursesStatus(courseRecord);
    			}
    		}
			return "change_status";
    }
    
    @GetMapping(value = "/filter_courses")
    public String filterCourse(Model model) {
    	List<Course> courses = courseService.getAllActiveCourses();
    	model.addAttribute("course", new Course());
    	model.addAttribute("courses", courses);
    	return "filter_courses_by_status";
    }
    
    @PostMapping(value = "/filter_courses_by_status")
    public String filterCourseByStatus(@ModelAttribute("course") Course course, Model model) {
    	List<Course> courses = null;
    	if(course.getCourseStatus().equals("Active")) {
    		courses = courseService.getAllActiveCourses();
    	} 
    	else {
    		courses = courseService.getAllInactiveCourses();
    	}
    			
    	model.addAttribute("course", new Course());
    	model.addAttribute("courses", courses);
    	return "filter_courses_by_status";
    }
    
 // set of values applied to a drop-down list.
 	@ModelAttribute("singleSelectAllValues")
     public String[] getSingleSelectAllValues() {
         return new String[] {"Active", "Inactive"};
     }
     */
}