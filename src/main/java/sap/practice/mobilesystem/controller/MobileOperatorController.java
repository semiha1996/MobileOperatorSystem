package sap.practice.mobilesystem.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String getMenuPage(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") != null && session.getAttribute("ID") != null) {
			String role = (String) session.getAttribute("ROLE");
			if (role.equals("operator")) {
				List<MobilePlan> mobilePlans = mobilePlanService.getAllMobilePlans();
				model.addAttribute("services", mobilePlans);

				model.addAttribute("searchTerm", new SearchTerm());

				Customer customer = new Customer();
				customer.setServices(new MobilePlan());

				model.addAttribute("customer", customer);

				model.addAttribute("serviceName", new String());
				model.addAttribute("service", new MobilePlan());
				return "menu";
			} else {
				return "redirect:/index";
			}
		} else {
			return "redirect:/index";
		}
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
			customer.setRole("customer");
			mobileServiceId = mobilePlans.get(0).getServiceId();

			Long customerId = userService.saveCustomer(customer);
			List<CustomerMobilePlan> customerPlanEntities = customerMobilePlanService
					.getAllCustomerMobilePlansById(customerId, mobileServiceId);

			if (customerPlanEntities.size() > 0) {
				CustomerMobilePlan customerPlanEnt = customerPlanEntities.get(0);
				Usage customerUsageEntity = new Usage();
				LocalDateTime dateTime = LocalDateTime.now();

				Date dateToPay = convertToDateViaSqlTimestamp(dateTime.plusMonths(1));
				customerUsageEntity.setCustomerServiceId(customerPlanEnt);
				customerUsageEntity.setDateToBePayed(dateToPay);
				customerUsageEntity.setMegabytesLeft(mobilePlans.get(0).getMegabytes());
				customerUsageEntity.setSmsLeft(mobilePlans.get(0).getSmsNumber());
				customerUsageEntity.setMinutesLeft(mobilePlans.get(0).getMinutes());
				usageService.saveUsage(customerUsageEntity);

				Payings customerNewPaying = new Payings();
				Date dateOfPaying = new Date();

				dateOfPaying = convertToDateViaSqlTimestamp(dateTime);
				customerNewPaying.setDateOfPaying(dateOfPaying);
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
	public String getCustomerPage(@RequestParam Long id, String role, Model model, HttpSession session) {
		if (session.getAttribute("ROLE") != null && session.getAttribute("ID") != null) {
			String userRole = (String) session.getAttribute("ROLE");
			if (userRole.equals("operator")) {
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
			} else {
				return "redirect:/index";
			}
		} else {
			return "redirect:/index";
		}
	}

	@PostMapping(value = "/updateCustomer")
	public String getUpdateCustomerPage(@ModelAttribute Customer customer,
			@ModelAttribute CustomerPlanId customerCurrentPlanId, @RequestParam Long id, Model model) {
		customer.setCustomerId(id);
		Long newServiceId = customer.getServices().getServiceId();

		List<MobilePlan> mobilePlans = mobilePlanService.getMobilePlansById(newServiceId);
		if (mobilePlans.size() > 0) {

			customer.setServices(mobilePlans.get(0));
		}

		userService.updateCustomer(customer);

		model.addAttribute("statusText", "Customer updated successfully");
		return "status";
	}

	// customerMenu page, get customer's current services and show their properties
	@GetMapping(value = "/customerMenu")
	public String getCustomerMenuPage(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") != null && session.getAttribute("ID") != null) {
			String userRole = (String) session.getAttribute("ROLE");
			if (userRole.equals("customer")) {
				Long customerId = (Long) session.getAttribute("ID");
				List<Customer> customers = userService.getCustomersById(customerId);
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

				return "customerMenu";
			} else {
				return "redirect:/index";
			}
		} else {
			return "redirect:/index";
		}
	}

	@PostMapping(value = "/updateCustomerData")
	public String getUpdateSelfCustomerPage(@ModelAttribute Customer customer, Model model) {
		userService.saveCustomer(customer);
		model.addAttribute("statusText", "Customer updated successfully");
		return "status";
	}

	@GetMapping(value = "/inquire")
	public String getInquirePage(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") != null && session.getAttribute("ID") != null) {
			String userRole = (String) session.getAttribute("ROLE");
			if (userRole.equals("operator")) {
				Date today = convertToDateViaSqlTimestamp(LocalDateTime.now());

				List<Usage> allUsages = usageService.getAllUsage();
				List<Customer> duePayCustomers = new ArrayList<Customer>();

				for (Usage usage : allUsages) {
					if (usage.getDateToBePayed().before(today)) {
						Long customerId = usage.getCustomerServiceId().getCustomerId();
						List<Customer> customers = userService.getCustomersById(customerId);
						if (customers.size() > 0) {
							duePayCustomers.add(customers.get(0));
						}
					}
				}
				SearchTerm searchTerm = new SearchTerm();
				searchTerm.setSearchCategory("Inquire");
				model.addAttribute("searchTerm", searchTerm);
				model.addAttribute("customers", duePayCustomers);
				return "search";
			} else {
				return "redirect:/index";
			}
		} else {
			return "redirect:/index";
		}
	}

	@GetMapping(value = "/login")
	public String getloginPage(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") == null && session.getAttribute("ID") == null) {
			model.addAttribute("customer", new Customer());
			if (session.getAttribute("ERROR") != null) {
				model.addAttribute("loginError", true);
				session.removeAttribute("ERROR");
			}
			return "login";
		} else {
			return "redirect:/index";
		}
	}

	@PostMapping(value = "/tryLogin")
	public String tryLogin(@ModelAttribute Customer customer, HttpServletRequest request, Model model) {
		List<Customer> customers = userService.getCustomersByUserNamePassword(customer.getUsername(),
				customer.getPassword());
		if (customers.size() > 0) {
			Customer foundCustomer = customers.get(0);
			request.getSession().setAttribute("ID", foundCustomer.getCustomerId());
			request.getSession().setAttribute("ROLE", foundCustomer.getRole());
			if (foundCustomer.getRole().equals("operator")) {
				return "redirect:/menu";
			} else if (foundCustomer.getRole().equals("customer")) {
				return "redirect:/customerMenu";
			} else {
				return "redirect:/index";
			}
		}
		request.getSession().setAttribute("ERROR", true);
		return "redirect:/login";
	}

	@PostMapping(value = "/tryLogout")
	public String tryLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		System.out.println("Session Over");
		return "redirect:/index";
	}

	@GetMapping(value = "/index")
	public String getHomePage(Model model, HttpSession session) {
		if (session.getAttribute("ROLE") == null && session.getAttribute("ID") == null) {
			model.addAttribute("isLogged", false);
		} else {
			model.addAttribute("isLogged", true);
		}
		return "index";
	}
}
