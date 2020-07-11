package tu.practice.mobile_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tu.practice.mobile_system.entity.CustomerServiceEntity;
import tu.practice.mobile_system.repository.CustomerServiceRepository;

@Service
public class CustomerServiceService {
	@Autowired
    private CustomerServiceRepository repository;
	
	public List<CustomerServiceEntity> getAllCustomerServices() {
		List<CustomerServiceEntity> services = repository.findAll();
		return services;
	}
	
}