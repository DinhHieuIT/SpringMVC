package dinhhieumvc.service;

import java.util.List;

import dinhhieumvc.model.RatingModel;


public interface RatingService  {
	public List<RatingModel> findAllRatingUser(RatingModel ratingModel);
}
