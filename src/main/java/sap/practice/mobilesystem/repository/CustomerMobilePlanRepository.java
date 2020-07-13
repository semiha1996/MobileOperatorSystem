package sap.practice.mobilesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.CustomerMobilePlan;

@Repository
public interface CustomerMobilePlanRepository extends JpaRepository<CustomerMobilePlan, Long> {
	@Query("SELECT a FROM CustomerMobilePlan a WHERE a.customerId = :customerId AND a.serviceId = :serviceId")
	List<CustomerMobilePlan> findAllEntitiesById(@Param("customerId") Long customerId,
			@Param("serviceId") Long serviceId);
}
