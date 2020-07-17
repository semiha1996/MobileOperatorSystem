package sap.practice.mobilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sap.practice.mobilesystem.entity.Usage;
import sap.practice.mobilesystem.repository.UsageRepository;

@Service
public class UsageService {
	@Autowired
	private UsageRepository repository;

	public List<Usage> getAllUsage() {
		List<Usage> usage = repository.findAll();
		return usage;
	}

	public List<Usage> getUsageById(Long id) {
		List<Usage> usage = repository.findAllUsageById(id);
		return usage;
	}

	public void saveUsage(Usage usage) {
		repository.saveAndFlush(usage);
	}

}
