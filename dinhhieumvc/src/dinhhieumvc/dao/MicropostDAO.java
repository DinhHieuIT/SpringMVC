package dinhhieumvc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dinhhieumvc.entity.Micropost;
import dinhhieumvc.entity.Rating;

public interface MicropostDAO extends GenericDAO<Micropost, Integer> {
	public Page<Micropost> paginate(Micropost micropost, Pageable pageable);
	public Double avgScore(Rating rating);
}
 