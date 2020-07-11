package tu.practice.mobile_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.Administrator;
import tu.practice.mobile_system.entity.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
