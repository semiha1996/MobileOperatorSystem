package tu.practice.mobile_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT a FROM Customer a WHERE lower(a.name) LIKE %:searchTerm% OR a.phone LIKE %:searchTerm%")
	  List<Customer> findAllByNameOrPhone(@Param("searchTerm") String searchTerm);
}