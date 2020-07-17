package sap.practice.mobilesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Payings;

@Repository
public interface PayingsRepository extends JpaRepository<Payings, Long> {
	@Query("SELECT a FROM Payings a WHERE a.id = :id")
	List<Payings> findAllPayingsById(@Param("id")Long id);
}
