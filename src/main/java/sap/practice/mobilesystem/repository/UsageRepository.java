package sap.practice.mobilesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Usage;


@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {
	@Query("SELECT a FROM Usage a WHERE a.customerServiceId.id = :id")
	List<Usage> findAllUsageById(@Param("id")Long id);
}
