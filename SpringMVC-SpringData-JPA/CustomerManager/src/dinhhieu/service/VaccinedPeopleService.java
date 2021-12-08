package dinhhieu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dinhhieu.entity.VaccinedPeople;
import dinhhieu.repository.VaccinedPeopleRepository;

@Service
@Transactional
public class VaccinedPeopleService {
	@Autowired VaccinedPeopleRepository repo;
	
	public void save(VaccinedPeople customer) {
		repo.save(customer);
	}
	
	public List<VaccinedPeople> listAll() {
		return (List<VaccinedPeople>) repo.findAll();
	}
	
	public VaccinedPeople get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public List<VaccinedPeople> search(String keyword) {
		return repo.search(keyword);
	}
}
