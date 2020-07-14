package sap.practice.mobilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.practice.mobilesystem.entity.CustomerMobilePlan;
import sap.practice.mobilesystem.repository.CustomerMobilePlanRepository;

@Service
public class CustomerMobilePlanService {
	@Autowired
	private CustomerMobilePlanRepository repository;

	public List<CustomerMobilePlan> getAllCustomerMobilePlans() {
		List<CustomerMobilePlan> services = repository.findAll();
		return services;
	}

	public List<CustomerMobilePlan> getAllCustomerMobilePlansById(Long customerId, Long serviceId) {
		List<CustomerMobilePlan> services = repository.findAllEntitiesById(customerId, serviceId);
		return services;
	}

	public void saveCustomerMobilePlanEntity(CustomerMobilePlan entity) {
		repository.saveAndFlush(entity);
	}

}
