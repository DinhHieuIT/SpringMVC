package dinhhieumvc.dao;

import java.util.List;

import dinhhieumvc.entity.Rating;

public interface RatingDAO extends GenericDAO<Rating, Integer> {
	public boolean isRating(Rating rating);
	public List<Rating> findRating(Rating rating);
}
