package sap.practice.mobilesystem.controller;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sap.practice.mobilesystem.entity.Customer;
import sap.practice.mobilesystem.entity.CustomerMobilePlan;
import sap.practice.mobilesystem.entity.MobilePlan;
import sap.practice.mobilesystem.entity.Payings;
import sap.practice.mobilesystem.entity.Usage;
import sap.practice.mobilesystem.service.AdministratorService;
import sap.practice.mobilesystem.service.CustomerMobilePlanService;
import sap.practice.mobilesystem.service.CustomerService;
import sap.practice.mobilesystem.service.MobilePlanService;
import sap.practice.mobilesystem.service.PayingsService;
import sap.practice.mobilesystem.service.UsageService;
import sap.practice.mobilesystem.utilities.CustomerPlanId;
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

	@Autowired
	private UsageService usageService;

	// menu page
	// service(s) <==> mobilePlan(s)
	@GetMapping(value = "/menu")
	public String getMenuPage(Model model) {
		List<MobilePlan> mobilePlans = mobilePlanService.getAllMobilePlans();
		model.addAttribute("services", mobilePlans);

		model.addAttribute("searchTerm", new SearchTerm());

		Customer customer = new Customer();
		customer.setServices(new MobilePlan());

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
			List<MobilePlan> mobilePlans = mobilePlanService
					.getAllMobilePlansByName(searchTerm.getSearchText().toLowerCase());
			model.addAttribute("services", mobilePlans);
		}

		model.addAttribute("searchTerm", searchTerm);
		return "search";
	}

	// add new customer and choose a service to assign to him
	@PostMapping(value = "/addCustomer")
	public String getAddCustomerPage(@ModelAttribute Customer customer, Model model) {
		List<MobilePlan> mobilePlans = mobilePlanService.getMobilePlansById(customer.getServices().getServiceId());

		Long mobileServiceId = 0L;
		if (mobilePlans.size() > 0) {
			customer.setServices(mobilePlans.get(0));
			mobileServiceId = mobilePlans.get(0).getServiceId();

			Long customerId = userService.saveCustomer(customer);
			List<CustomerMobilePlan> customerPlanEntities = customerMobilePlanService
					.getAllCustomerMobilePlansById(customerId, mobileServiceId);

			if (customerPlanEntities.size() > 0) {
				CustomerMobilePlan customerPlanEnt = customerPlanEntities.get(0);
				Usage customerUsageEntity = new Usage();
				LocalDateTime dateTime = LocalDateTime.now();
				Date dateToPay = convertToDateViaSqlTimestamp(dateTime);
				customerUsageEntity.setCustomerServiceId(customerPlanEnt);
				customerUsageEntity.setDateToBePayed(dateToPay);
				customerUsageEntity.setMegabytesLeft(mobilePlans.get(0).getMegabytes());
				customerUsageEntity.setSmsLeft(mobilePlans.get(0).getSmsNumber());
				customerUsageEntity.setMinutesLeft(mobilePlans.get(0).getMinutes());
				usageService.saveUsage(customerUsageEntity);

				Payings customerNewPaying = new Payings();
				dateTime = dateTime.plusMonths(1);
				dateToPay = convertToDateViaSqlTimestamp(dateTime);
				customerNewPaying.setDateOfPaying(dateToPay);
				customerNewPaying.setCustomerServiceRelations(customerPlanEnt);
				payingsService.savePayings(customerNewPaying);

			}
		}
		model.addAttribute("statusText", "User added successfully");
		return "status";
	}

	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
		return java.sql.Timestamp.valueOf(dateToConvert);
	}

	// add a new service and save it in DB
	@PostMapping(value = "/addService")
	public String getAddServicePage(@ModelAttribute MobilePlan mobileService, Model model) {

		mobilePlanService.saveNewMobilePlan(mobileService);

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
		CustomerPlanId currentPlanId = new CustomerPlanId();
		currentPlanId.setPlanId(customer.getServices().getServiceId());
		model.addAttribute("customer", customer);
		model.addAttribute("customerCurrentPlanId", currentPlanId);

		List<MobilePlan> mobilePlans = mobilePlanService.getAllMobilePlans();
		model.addAttribute("services", mobilePlans);

		return "customer";
	}

	@PostMapping(value = "/updateCustomer")
	public String getUpdateCustomerPage(@ModelAttribute Customer customer,
			@ModelAttribute CustomerPlanId customerCurrentPlanId, @RequestParam Long id, Model model) {
		customer.setCustomerId(id);
		Long newServiceId = customer.getServices().getServiceId();

		CustomerMobilePlan customerMobilePlan = new CustomerMobilePlan();
		List<MobilePlan> mobilePlans = mobilePlanService.getMobilePlansById(newServiceId);
		if (mobilePlans.size() > 0) {

			List<CustomerMobilePlan> customerServiceEntities = customerMobilePlanService
					.getAllCustomerMobilePlansById(customer.getCustomerId(), customerCurrentPlanId.getPlanId());

			System.out.println(customerServiceEntities.get(0).getId());

			customer.setServices(mobilePlans.get(0));
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

	// customerMenu page, get customer's current services and show their properties
	@GetMapping(value = "/customer_menu")
	public String getCustomerMenuPage(Model model) {

		List<Customer> customers = userService.getCustomersById(1L);
		Customer customer = new Customer();
		if (customers.size() > 0) {
			customer = customers.get(0);
		}
		model.addAttribute("customer", customer);

		MobilePlan mobilePlan = new MobilePlan();
		Usage usage = new Usage();
		if (customer.getServices() != null) {
			mobilePlan = customer.getServices();

			List<CustomerMobilePlan> customerServiceEntities = customerMobilePlanService
					.getAllCustomerMobilePlansById(customer.getCustomerId(), mobilePlan.getServiceId());

			if (customerServiceEntities.size() > 0) {
				Long customerPlanId = customerServiceEntities.get(0).getId();
				List<Usage> usages = usageService.getUsageById(customerPlanId);
				if (usages.size() > 0) {
					usage = usages.get(0);
				}
			}
		}
		model.addAttribute("service", mobilePlan);
		model.addAttribute("usage", usage);
		model.addAttribute("customer", customer);
		

		return "customer_menu";
	}
	
	@PostMapping(value = "/updateCustomerData")
	public String getUpdateSelfCustomerPage(@ModelAttribute Customer customer, Model model) {
		userService.saveCustomer(customer);
		model.addAttribute("statusText", "Customer updated successfully");
		return "status";
	}

	@GetMapping(value = "/index")
	public String getHomePage(Model model) {
		return "index";
	}
	/*
	 * @PostMapping(value = "/login") public String getLogin(@ModelAttribute User
	 * user, Model model) { model.addAttribute("username", new String());
	 * model.addAttribute("password", new String());
	 * 
	 * return "/login"; }
	 * 
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
	 * 
	 * @RequestMapping("/login.html") public String login() { return "login.html"; }
	 * 
	 * // Login form with error
	 * 
	 * @PostMapping(value = "/error") public String loginError(Model model) {
	 * model.addAttribute("loginError", true); return "login"; }
	 */
}
