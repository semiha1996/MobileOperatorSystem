package tu.practice.mobile_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tu.practice.mobile_system.entity.MobileServiceEntity;

@Repository
public interface MobileServiceRepository extends JpaRepository<MobileServiceEntity, Long>{
	@Query("SELECT a FROM MobileServiceEntity a WHERE a.name LIKE %:searchName%  ")
	  List<String> findDistinctEvents(@Param("searchName") String searchName);

}
