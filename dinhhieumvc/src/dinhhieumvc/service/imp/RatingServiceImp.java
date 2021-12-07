package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.RatingDAO;
import dinhhieumvc.dao.UserDAO;
import dinhhieumvc.entity.Rating;
import dinhhieumvc.model.RatingModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.RatingService;

public class RatingServiceImp implements RatingService {
	
	private static final Logger logger = LoggerFactory.getLogger(RatingServiceImp.class);
	
	@Autowired
	RatingDAO ratingDAO;
	
	@Autowired
	UserDAO userDAO;
	
	public void setRatingDAO(RatingDAO ratingDAO) {
		this.ratingDAO = ratingDAO;
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional(readOnly = true)
	public List<RatingModel> findAllRatingUser(RatingModel ratingModel){
	
		logger.info("find User from Rating in the database");
		try {
			Rating ratingEntity = new Rating();
			ratingEntity.setMicropostId(ratingModel.getMicropostId());
			List<Rating> ratings = ratingDAO.findRating(ratingEntity);
			
			List<RatingModel> results = new ArrayList<RatingModel>();
			for (Rating rating : ratings) {
				RatingModel model = new RatingModel();
				BeanUtils.copyProperties(rating, model);
				UserModel user = new UserModel();
				BeanUtils.copyProperties(rating.getUser(), user);
				model.setUser(user);
				results.add(model);
	        };
	        return results;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}
