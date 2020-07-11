package tu.practice.mobile_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}