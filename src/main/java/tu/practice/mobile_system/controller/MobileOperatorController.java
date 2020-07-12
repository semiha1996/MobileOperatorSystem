package tu.practice.mobile_system.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import tu.practice.mobile_system.classes.SearchTerm;
import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.entity.Customer;
import tu.practice.mobile_system.entity.CustomerServiceEntity;
import tu.practice.mobile_system.entity.MobileServiceEntity;
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
    
    //menu page
    @GetMapping(value = "/menu")
    public String getMenuPage(Model model) {
    	List<MobileServiceEntity> services = mobileServiceService.getAllMobileServices();
		model.addAttribute("services", services);
		
    	model.addAttribute("searchTerm", new SearchTerm());
    	
    	Customer customer = new Customer();
    	customer.setServices(new ArrayList<MobileServiceEntity>());
    	customer.getServices().add(new MobileServiceEntity());
    	model.addAttribute("customer", customer);
    	
    	model.addAttribute("serviceName", new String());
    	model.addAttribute("service", new MobileServiceEntity());
        return "menu";
    }
    //search in category customers or services and return search page
    @PostMapping(value = "/search")
    public String getSearchPage(@ModelAttribute SearchTerm searchTerm, Model model) {
    	
    	if(searchTerm.getSearchCategory().equals("Customers")) {
    		List<Customer> customers = userService.getCustomersByNameOrPhone(searchTerm.getSearchText().toLowerCase());
        	model.addAttribute("customers", customers);
    	}
    	else {
    		List<MobileServiceEntity> services = mobileServiceService.getAllMobileServices();
    		model.addAttribute("services", services);
    	}
    	
    	model.addAttribute("searchTerm", searchTerm);
        return "search";
    }
    
    //add new customer and choose a servise to assign to him 
    @PostMapping(value = "/addCustomer")
    public String getAddCustomerPage(@ModelAttribute Customer customer, Model model) {
    	List<MobileServiceEntity> mobileServices = 
    			mobileServiceService.getMobileServiceById(customer.getServices().get(0).getServiceId());
    	
    	customer.getServices().clear();
    	Long mobileServiceId = 0L;
    	if(mobileServices.size() > 0) {
    		customer.getServices().add(mobileServices.get(0));
    		mobileServiceId = mobileServices.get(0).getServiceId();
    	
	    	Long customerId = userService.saveCustomer(customer);
	    	List<CustomerServiceEntity> customerServiceEntities = 
    			customerServiceService.getAllCustomerServicesById(customerId, mobileServiceId);
	    	
	    	if(customerServiceEntities.size() > 0) {
	    		CustomerServiceEntity customerServiceEnt = customerServiceEntities.get(0);
	    		LocalDateTime date = LocalDateTime.now();
	    		Long day = (long) date.getDayOfMonth();
	    		customerServiceEnt.setDateToBePayed(day);
	    		customerServiceEnt.setMegabytesLeft(mobileServices.get(0).getMegabytes());
	    		customerServiceEnt.setSmsLeft(mobileServices.get(0).getSmsNumber());
	    		customerServiceEnt.setMinutesLeft(mobileServices.get(0).getMinutes());
	    		
	    		//save customer in DB
	    		customerServiceService.saveServiceCustomerEntity(customerServiceEnt);
	    	}
    	}
    	model.addAttribute("statusText", "User added successfully");
        return "status";
    }
    //add a new service and save it in DB
    @PostMapping(value = "/addService")
    public String getAddServicePage(@ModelAttribute MobileServiceEntity mobileService, Model model) {
    	
    	mobileServiceService.saveNewService(mobileService);
    	
    	model.addAttribute("statusText", "Service added successfully");
        return "status";
    }
    
    @GetMapping(value = "/customer")
    public String getCustomerPage(@RequestParam Long id, Model model) {
    	List<Customer> customers = userService.getCustomersById(id);
    	Customer customer = new Customer();
    	if(customers.size() > 0) {
    		customer = customers.get(0);
    	}
    	
    	model.addAttribute("customer", customer);
        return "customer";
    }
    
    @PostMapping(value = "/updateCustomer")
    public String getUpdateCustomerPage(@ModelAttribute Customer customer, 
    		@RequestParam Long id, Model model) {
    	customer.setCustomerId(id);
    	Long customerId = userService.updateCustomer(customer);
    	
    	model.addAttribute("statusText", "Customer updated successfully");
        return "status";
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
