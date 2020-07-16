package sap.practice.mobilesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sap.practice.mobilesystem.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT a FROM Customer a WHERE lower(a.name) LIKE %:searchTerm% OR a.phone LIKE %:searchTerm%")
	List<Customer> findAllByNameOrPhone(@Param("searchTerm") String searchTerm);

	@Query("SELECT a FROM Customer a WHERE a.customerId = :id AND a.role= :role")
	List<Customer> findAllCustomersById(@Param("id") Long id, @Param("role") String role);
	
	@Query("SELECT a FROM Customer a WHERE a.username = :userName AND a.password = :password")
	List<Customer> findAllCustomersByUserNamePassword(@Param("userName") String userName, @Param("password") String password);
}