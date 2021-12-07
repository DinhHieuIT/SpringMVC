package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.BookmarkDAO;
import dinhhieumvc.dao.BuyDAO;
import dinhhieumvc.dao.CommentDAO;
import dinhhieumvc.dao.LikeDAO;
import dinhhieumvc.dao.MicropostDAO;
import dinhhieumvc.dao.RatingDAO;
import dinhhieumvc.entity.Bookmark;
import dinhhieumvc.entity.Likes;
import dinhhieumvc.entity.Micropost;
import dinhhieumvc.entity.Rating;
import dinhhieumvc.model.BookmarkModel;
import dinhhieumvc.model.CommentModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.MicropostService;

@Service
public class MicropostServiceImp implements MicropostService {

	private static final Logger logger = LoggerFactory.getLogger(MicropostServiceImp.class);

	@Autowired
	MicropostDAO micropostDAO;
	
	@Autowired
	CommentDAO commentDAO;
	
	@Autowired
	RatingDAO ratingDAO;
	
	@Autowired
	LikeDAO likeDAO;
	
	@Autowired
	BuyDAO buyDAO;
	
	@Autowired
	BookmarkDAO bookmarkDAO;
	
	private MicropostServiceImp() {
	}

	public void setMicropostDAO(MicropostDAO micropostDAO) {
		this.micropostDAO = micropostDAO;
	}

	@Transactional(readOnly = true)
	public Page<MicropostModel> paginate(MicropostModel micropostModel) {
		try {
			Micropost condition = new Micropost();
			condition.setUserId(micropostModel.getUserId());
			Page<Micropost> microposts = micropostDAO.paginate(condition, micropostModel.getPageable());
		
			return microposts.map(micropost -> {
				MicropostModel model = new MicropostModel();
				BeanUtils.copyProperties(micropost, model);
				UserModel user = new UserModel();
				BeanUtils.copyProperties(micropost.getUser(), user);
				model.setUser(user);
				Likes likes = new Likes();
				likes.setUserId(user.getId());
				likes.setMicropostId(micropost.getId());
				model.setLikeByCurrentUser(likeDAO.isLike(likes));
				logger.info("Checking the in Buy");
				Bookmark bookmark = new Bookmark();
				bookmark.setUserId(user.getId());
				bookmark.setMicropostId(micropost.getId());

				model.setBookmarkByCurrentUser(bookmarkDAO.isBookmark(bookmark));
				
				List<CommentModel> comments = new ArrayList<CommentModel>();
				micropost.getComments().forEach(comment -> {
					if(comment != null) {
						CommentModel commentModel = new CommentModel();
						BeanUtils.copyProperties(comment, commentModel);
						UserModel user1 = new UserModel();
						BeanUtils.copyProperties(comment.getUser(), user1);
						commentModel.setUser(user1);
						comments.add(commentModel);
					}
				});
				
				model.setComments(comments);
				Rating rating = new Rating();
				rating.setMicropostId(micropostModel.getId());
				model.setTotalRating(micropostDAO.avgScore(rating));
				
				return model;
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public int count(MicropostModel micropostModel) {
		logger.info("Counting the micropost in the database");
		try {
			return micropostDAO.count(Restrictions.eq("userId", micropostModel.getUserId()));
		} catch (Exception e) {
			logger.error("An error occurred while counting the micropost details from the database", e);
			return 0;
		}
	}

	@Transactional
	public MicropostModel addMicropost(MicropostModel micropostModel) throws Exception {
		logger.info("Adding the micropost in the database");
		try {
			Micropost condition = new Micropost();
			condition.setUserId(micropostModel.getUserId());
			condition.setContent(micropostModel.getContent());
			Micropost micropost = micropostDAO.makePersistent(condition);
			micropostModel = new MicropostModel();
			BeanUtils.copyProperties(micropost, micropostModel);
			return micropostModel;
		} catch (Exception e) {
			logger.error("An error occurred while adding the micropost details to the database", e);
			throw e;
		}
	}

	public MicropostModel findMicropost(Integer id, HttpServletRequest request) {
		logger.info("Checking the micropost in the database");
		try {
			Micropost micropost = micropostDAO.find(id);
			MicropostModel micropostModel = null;
			Likes like = new Likes();
			UserModel user =(UserModel) request.getSession().getAttribute("user");
			like.setUserId(user.getId());
			if (micropost != null) {
				micropostModel = new MicropostModel();
				BeanUtils.copyProperties(micropost, micropostModel);
				
				if(likeDAO.isLike(like)) {
					micropostModel.setLikeByCurrentUser(true);
				}else {
					micropostModel.setLikeByCurrentUser(false);
				}
			}
			return micropostModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the micropost details from the database", e);
			return null;
		}
	}
	
	public Double getMicropostRatingScore(UserModel user, MicropostModel micropost) {
		logger.info("Fetching the user info in the database");
		    Rating rating = new Rating();
		    rating.setUserId(user.getId());
		    rating.setMicropostId(micropost.getId());
            return micropostDAO.avgScore(rating);
        
		}
	
	@Transactional
	public boolean rating(UserModel user, MicropostModel micropost, Integer rateScore) throws Exception {
		logger.info("Rating miropost into in the database");
		try {
			Rating rating = new Rating();
			rating.setUserId(user.getId());
			rating.setMicropostId(micropost.getId());
			rating.setRateScore(rateScore);
			
			if(ratingDAO.isRating(rating)) {
				logger.info("Rated miropost into in the database");
				return false;
			} else {
				logger.info("Rate miropost into in the database");
				ratingDAO.makePersistent(rating);
				return true;
			}
		} catch (Exception e) {
			logger.error("Rating miropost into in the database", e);
			throw e;
		}	
	}
	
	@Transactional
	public BookmarkModel bookmark(UserModel user, MicropostModel micropost) throws Exception {
		logger.info("bookmark into in the database");
		try {
			Bookmark bookmark = new Bookmark();
			bookmark.setUserId(user.getId());
			bookmark.setMicropostId(micropost.getId());
	
		
			if(bookmarkDAO.load(bookmark) != null) {
				logger.info("Rated miropost into in the database");
				BookmarkModel bookmarkModel = new BookmarkModel();
				BeanUtils.copyProperties(bookmark,bookmarkModel );
				bookmarkModel.setBookmarkedByCurrentUser(true);
				return bookmarkModel;
				
			} else {
				logger.info("Rate miropost into in the database");
				bookmarkDAO.makePersistent(bookmark);
				BookmarkModel bookmarkModel = new BookmarkModel();
				BeanUtils.copyProperties(bookmark,bookmarkModel );
				return bookmarkModel;
			}
		} catch (Exception e) {
			logger.error("Rating miropost into in the database", e);
			throw e;
		}
		
		
	}

	

	}

