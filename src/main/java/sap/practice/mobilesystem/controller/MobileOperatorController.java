package sap.practice.mobilesystem.controller;

import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sap.practice.mobilesystem.entity.Customer;
import sap.practice.mobilesystem.entity.CustomerMobilePlan;
import sap.practice.mobilesystem.entity.MobilePlan;
import sap.practice.mobilesystem.service.AdministratorService;
import sap.practice.mobilesystem.service.CustomerMobilePlanService;
import sap.practice.mobilesystem.service.CustomerService;
import sap.practice.mobilesystem.service.MobilePlanService;
import sap.practice.mobilesystem.service.PayingsService;
import sap.practice.mobilesystem.utilities.SearchTerm;


@Controller
public class MobileOperatorController {
	@Autowired
	private AdministratorService adminService;

	@Autowired
	private CustomerService userService;

	@Autowired
	private CustomerMobilePlanService customerMobilePlanService;

	@Autowired
	private MobilePlanService mobilePlanService;

	@Autowired
	private PayingsService payingsService;

	// menu page
	@GetMapping(value = "/menu")
	public String getMenuPage(Model model) {
		List<MobilePlan> services = mobilePlanService.getAllMobileServices();
		model.addAttribute("services", services);

		model.addAttribute("searchTerm", new SearchTerm());

		Customer customer = new Customer();
		customer.setServices(new ArrayList<MobilePlan>());
		customer.getServices().add(new MobilePlan());
		model.addAttribute("customer", customer);

		model.addAttribute("serviceName", new String());
		model.addAttribute("service", new MobilePlan());
		return "menu";
	}

	// search in category customers or services and return search page
	@PostMapping(value = "/search")
	public String getSearchPage(@ModelAttribute SearchTerm searchTerm, Model model) {

		if (searchTerm.getSearchCategory().equals("Customers")) {
			List<Customer> customers = userService.getCustomersByNameOrPhone(searchTerm.getSearchText().toLowerCase());
			model.addAttribute("customers", customers);
		} else {
			List<MobilePlan> services = mobilePlanService.getAllMobileServicesByName(searchTerm.getSearchText().toLowerCase());
			model.addAttribute("services", services);
		}

		model.addAttribute("searchTerm", searchTerm);
		return "search";
	}

	// add new customer and choose a service to assign to him
	@PostMapping(value = "/addCustomer")
	public String getAddCustomerPage(@ModelAttribute Customer customer, Model model) {
		List<MobilePlan> mobileServices = mobilePlanService
				.getMobileServiceById(customer.getServices().get(0).getServiceId());

		customer.getServices().clear();
		Long mobileServiceId = 0L;
		if (mobileServices.size() > 0) {
			customer.getServices().add(mobileServices.get(0));
			mobileServiceId = mobileServices.get(0).getServiceId();

			Long customerId = userService.saveCustomer(customer);
			List<CustomerMobilePlan> customerServiceEntities = customerMobilePlanService.getAllCustomerServicesById(customerId, mobileServiceId);

			if (customerServiceEntities.size() > 0) {
				CustomerMobilePlan customerServiceEnt = customerServiceEntities.get(0);
				LocalDateTime date = LocalDateTime.now();
				Long day = (long) date.getDayOfMonth();
				customerServiceEnt.setDateToBePayed(day);
				customerServiceEnt.setMegabytesLeft(mobileServices.get(0).getMegabytes());
				customerServiceEnt.setSmsLeft(mobileServices.get(0).getSmsNumber());
				customerServiceEnt.setMinutesLeft(mobileServices.get(0).getMinutes());

				// save customer in DB
				customerMobilePlanService.saveCustomerMobilePlanEntity(customerServiceEnt);
			}
		}
		model.addAttribute("statusText", "User added successfully");
		return "status";
	}

	// add a new service and save it in DB
	@PostMapping(value = "/addService")
	public String getAddServicePage(@ModelAttribute MobilePlan mobileService, Model model) {

		mobilePlanService.saveNewService(mobileService);

		model.addAttribute("statusText", "Service added successfully");
		return "status";
	}

	@GetMapping(value = "/customer")
	public String getCustomerPage(@RequestParam Long id, Model model) {
		List<Customer> customers = userService.getCustomersById(id);
		Customer customer = new Customer();
		if (customers.size() > 0) {
			customer = customers.get(0);
		}
		model.addAttribute("customer", customer);
		
		List<MobilePlan> services = mobilePlanService.getAllMobileServices();
		model.addAttribute("services", services);
		
		return "customer";
	}

	@PostMapping(value = "/updateCustomer")
	public String getUpdateCustomerPage(@ModelAttribute Customer customer, @RequestParam Long id, Model model) {
		customer.setCustomerId(id);
		Long newServiceId = customer.getServices().get(0).getServiceId();

		List<MobilePlan> mobilePlans = mobilePlanService.getMobileServiceById(newServiceId);
		if(mobilePlans.size() > 0) {
			customer.getServices().clear();
			customer.getServices().add(mobilePlans.get(0));
		}
		
		userService.updateCustomer(customer);

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
	// customerMenu page, get all active services and show their properties
	@GetMapping(value = "/customer_menu")
	public String getCustomerMenuPage(@ModelAttribute MobilePlan service, Model model) {

		List<MobilePlan> services = mobilePlanService.getAllMobileServices();
		model.addAttribute("services", services);

		return "customer_menu";
	}

	
	 * protected void configure(final HttpSecurity http) throws Exception { http
	 * .formLogin() .loginPage("/login.html") .failureUrl("/login-error.html")
	 * .and() .logout() .logoutSuccessUrl("/index.html"); }
	 * 
	 * // Login form
	 * 
	 * @GetMapping(value = "/login") public String LoginPage(Model model) {
	 * model.addAttribute("course", new Customer()); return "login"; }
	 * 
	 * @PostMapping(value = "/login") public String login(@ModelAttribute username,
	 * String password, Model model) { model.addAttribute(username, password);
	 * return "/login"; }
	
	@RequestMapping("/login.html")
	public String login() {
		return "login.html";
	}

	// Login form with error
	@PostMapping(value = "/error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
 */
}
