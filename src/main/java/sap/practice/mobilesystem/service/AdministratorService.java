package sap.practice.mobilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sap.practice.mobilesystem.entity.Administrator;
import sap.practice.mobilesystem.repository.AdministratorRepository;

@Service
public class AdministratorService {
	@Autowired
	private AdministratorRepository repository;

	public List<Administrator> getAllAdmins() {
		List<Administrator> admins = repository.findAll();
		return admins;
	}

}
