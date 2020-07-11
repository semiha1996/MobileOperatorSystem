package tu.practice.mobile_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.CustomerServiceEntity;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerServiceEntity, Long>{

}
