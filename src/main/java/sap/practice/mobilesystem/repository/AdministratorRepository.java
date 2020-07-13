package sap.practice.mobilesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Administrator;
import sap.practice.mobilesystem.entity.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
