package sap.practice.mobilesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Usage;

@Repository
public interface UsageRepository {

	@Query("SELECT a FROM Usage a WHERE a.usageId = :id")
	List<Usage> findAll();

}
