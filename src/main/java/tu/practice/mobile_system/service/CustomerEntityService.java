package tu.practice.mobile_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tu.practice.mobile_system.entity.Customer;
import tu.practice.mobile_system.repository.CustomerRepository;

	@Service
	public class CustomerEntityService {
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
		
		public Long saveNewCustomer(Customer customer) {
			Customer savedCustomer = repository.saveAndFlush(customer);
			return savedCustomer.getCustomerId();
		}
}
