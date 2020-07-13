package sap.practice.mobilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.practice.mobilesystem.entity.Payings;
import sap.practice.mobilesystem.repository.PayingsRepository;

@Service
public class PayingsService {
	@Autowired
	private PayingsRepository repository;

	public List<Payings> getAllPayings() {
		List<Payings> payings = repository.findAll();
		return payings;
	}
}
