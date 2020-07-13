package sap.practice.mobilesystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.MobilePlan;

@Repository
public interface MobilePlanRepository extends JpaRepository<MobilePlan, Long> {
	@Query("SELECT a FROM MobilePlan a WHERE a.serviceId = :id")
	List<MobilePlan> getPlanById(@Param("id") Long id);
	
	@Query("SELECT a FROM MobilePlan a WHERE lower(a.name) LIKE %:searchTerm%")
	List<MobilePlan> getAllPlansByName(@Param("searchTerm") String searchTerm);

}
