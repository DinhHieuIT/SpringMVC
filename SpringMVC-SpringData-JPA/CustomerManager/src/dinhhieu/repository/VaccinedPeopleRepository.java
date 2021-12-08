package dinhhieu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dinhhieu.entity.VaccinedPeople;

public interface VaccinedPeopleRepository extends CrudRepository<VaccinedPeople, Long> {
	
	@Query(value = "SELECT vp FROM VaccinedPeople vp WHERE vp.name LIKE '%' || :keyword || '%'"
			+ " OR vp.phone LIKE '%' || :keyword || '%'"
			+ " OR vp.address LIKE '%' || :keyword || '%'")
	public List<VaccinedPeople> search(@Param("keyword") String keyword);
}