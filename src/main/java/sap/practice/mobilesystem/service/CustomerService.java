package sap.practice.mobilesystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sap.practice.mobilesystem.entity.Customer;
import sap.practice.mobilesystem.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repository;

	public List<Customer> getAllCustomers() {
		List<Customer> customers = repository.findAll();
		return customers;
	}

	public List<Customer> getCustomersByNameOrPhone(String searchTerm) {
		List<Customer> customers = repository.findAllByNameOrPhone(searchTerm);
		return customers;
	}

	public List<Customer> getCustomersById(Long id) {
		List<Customer> customers = repository.findAllCustomersById(id, "customer");
		return customers;
	}

	public List<Customer> getCustomersByUserNamePassword(String userName, String password) {
		List<Customer> customers = repository.findAllCustomersByUserNamePassword(userName, password);
		return customers;
	}

	public Long saveCustomer(Customer customer) {
		Customer savedCustomer = repository.saveAndFlush(customer);
		return savedCustomer.getCustomerId();
	}

	public Long updateCustomer(Customer customer) {
		Customer customerFromDb = repository.getOne(customer.getCustomerId());
		if (customerFromDb != null) {
			customerFromDb = customer;
			Customer savedCustomer = repository.save(customer);
			return savedCustomer.getCustomerId();
		}

		return 0L;
	}
}
