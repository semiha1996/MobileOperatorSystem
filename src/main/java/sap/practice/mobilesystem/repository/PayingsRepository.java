package sap.practice.mobilesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Payings;

@Repository
public interface PayingsRepository extends JpaRepository<Payings, Long> {

}
