package tu.practice.mobile_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tu.practice.mobile_system.entity.Customer;
import tu.practice.mobile_system.entity.MobileServiceEntity;
import tu.practice.mobile_system.repository.MobileServiceRepository;

@Service
public class MobileServiceService {
	@Autowired
    private MobileServiceRepository repository;
	
	public List<MobileServiceEntity> getAllMobileServices() {
		List<MobileServiceEntity> services = repository.findAll();
		return services;
	}
	
	public List<MobileServiceEntity> getMobileServiceById(Long id) {
		return repository.getServiceById(id);
	}
	
	public void saveNewService(MobileServiceEntity mobileService) {
		repository.saveAndFlush(mobileService);
	}
}
