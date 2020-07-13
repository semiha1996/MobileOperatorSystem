package sap.practice.mobilesystem.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.practice.mobilesystem.entity.MobilePlan;
import sap.practice.mobilesystem.repository.MobilePlanRepository;

@Service
public class MobilePlanService {
	@Autowired
	private MobilePlanRepository repository;

	public List<MobilePlan> getAllMobileServices() {
		List<MobilePlan> services = repository.findAll();
		return services;
	}
	
	public List<MobilePlan> getAllMobileServicesByName(String searchTerm) {
		List<MobilePlan> services = repository.getAllPlansByName(searchTerm);
		return services;
	}

	public List<MobilePlan> getMobileServiceById(Long id) {
		return repository.getPlanById(id);
	}

	public void saveNewService(MobilePlan mobileService) {
		repository.saveAndFlush(mobileService);
	}
}
