package tu.practice.mobile_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.CustomerServiceEntity;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerServiceEntity, Long>{
	@Query("SELECT a FROM CustomerServiceEntity a WHERE a.customerId = :customerId AND a.serviceId = :serviceId")
	  List<CustomerServiceEntity> findAllEntitiesById(@Param("customerId") Long customerId, @Param("serviceId") Long serviceId);
}
