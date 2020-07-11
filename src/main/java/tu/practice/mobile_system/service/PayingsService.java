package tu.practice.mobile_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tu.practice.mobile_system.entity.Payings;
import tu.practice.mobile_system.repository.PayingsRepository;

@Service
public class PayingsService {
	@Autowired
    private PayingsRepository repository;

	public List<Payings> getAllMobileServices() {
		List<Payings> payings = repository.findAll();
		return payings;
	}
}
