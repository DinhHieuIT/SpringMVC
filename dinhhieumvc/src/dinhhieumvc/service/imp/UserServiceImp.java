package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dinhhieumvc.dao.MicropostDAO;
import dinhhieumvc.dao.RatingDAO;
import dinhhieumvc.dao.RelationshipDAO;
import dinhhieumvc.dao.UserDAO;
import dinhhieumvc.entity.Rating;
import dinhhieumvc.entity.Relationship;
import dinhhieumvc.entity.Role;
import dinhhieumvc.entity.User;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.UserService;

@Service
public class UserServiceImp implements UserService  {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	
	@Autowired
	private UserDAO userDAO;
	

	@Autowired
	private RelationshipDAO relationshipDAO;

	@Autowired
	private MicropostDAO micropostDAO;
	
	@Autowired
	private RatingDAO ratingDAO;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public UserModel addUserFacebook(UserModel userModel) throws Exception {
		logger.info("Adding the user in the database");
		try {
			User condition = new User();
			condition.setName(userModel.getName());
			condition.setEmail(userModel.getEmail());
		    condition.setUid(userModel.getUid());
		    condition.setRole(Role.ADMIN_ROLE);
			User user = userDAO.makePersistent(condition);
			userModel = new UserModel();
			BeanUtils.copyProperties(user, userModel);
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while adding the user details to the database", e);
			throw e;
		}
	}
	
	@Transactional
	public UserModel addUser(UserModel userModel) throws Exception {
		logger.info("Adding the user in the database");
		try {
			User condition = new User();
			condition.setName(userModel.getName());
			condition.setEmail(userModel.getEmail());
			condition.setPassword(passwordEncoder.encode(userModel.getPassword()));
			condition.setRole(Role.USER_ROLE);
			User user = userDAO.makePersistent(condition);
			userModel = new UserModel();
			BeanUtils.copyProperties(user, userModel);
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while adding the user details to the database", e);
			throw e;
		}
	}

	public UserModel findUserByEmail(String email) {
		logger.info("Fetching the user by email in the database");
		try {
			User user = userDAO.findUserByEmail(email);
			UserModel userModel = null;
			if (user != null) {
				userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);
			}
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}
	}
	
	public UserModel findUserByUid(String uid) {
		logger.info("Find user by uid");
		try {
			User user = userDAO.findUserByUid(uid);
			UserModel userModel = null;
			if (user != null) {
				userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);
			}
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}
		
		
	}
	
	public List<UserModel> findAllUser() {
		logger.info("Fetching the user in the database");
		try {
			List<User> users = userDAO.findAllUser();
			List<UserModel> userModels = new ArrayList<UserModel>();
			if (users != null) {
				for(User user : users) {
			    UserModel userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);
				userModels.add(userModel);
				}
			}
			return userModels;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}
	}
	
	public List<UserModel> findRatingUser(Rating rating){
		
		logger.info("Fetching the rating user in the database");
		try {
			List<User> users = userDAO.findAllUser();
			List<UserModel> userModels = new ArrayList<UserModel>();
			if (users != null) {
				for(User user : users) {
			    UserModel userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);
				userModels.add(userModel);
				}
			}
			return userModels;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}

		
	}
	
	public UserModel findUser(Integer id) {
		logger.info("Checking the user in the database");
		try {
			User user = userDAO.find(id);
			UserModel userModel = null;
			if (user != null) {
				userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);
			}
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details from the database", e);
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public Page<UserModel> paginate(UserModel userModel) {
		try {
			Page<User> users = userDAO.paginate(userModel.getPageable());
			return users.map(user -> {
				UserModel model = new UserModel();
				BeanUtils.copyProperties(user, model);
				return model;
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Transactional
	public UserModel editUser(UserModel userModel) throws Exception {
		logger.info("Updating the user in the database");
		try {
			User user = userDAO.find(userModel.getId(), true);
			if (StringUtils.hasText(userModel.getName())) {
				user.setName(userModel.getName());
			}
			if (StringUtils.hasText(userModel.getEmail())) {
				user.setEmail(userModel.getEmail());
			}
			if (StringUtils.hasText(userModel.getPassword())) {
				user.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}
			userDAO.makePersistent(user);
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while updating the user details to the database", e);
			throw e;
		}
	}
	@Transactional
	public boolean follow(UserModel follower, UserModel followed) throws Exception {
		Relationship relationship = new Relationship();
		relationship.setFollowerId(follower.getId());
		relationship.setFollowedId(followed.getId());
		if(relationshipDAO.isFollowing(relationship)) {
			return false;
		} else {
			relationshipDAO.makePersistent(relationship);
			return true;
		}
	}
	
	
	
	@Transactional
	public boolean unfollow(UserModel follower, UserModel followed) {
		try {
			Relationship condition = new Relationship();
			condition.setFollowerId(follower.getId());
			condition.setFollowedId(followed.getId());
			Relationship relationship = relationshipDAO.load(condition);
			if (relationship != null) {
				relationshipDAO.makeTransient(relationship);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public UserModel getUserInfo(UserModel condition) {
		logger.info("Fetching the user info in the database");
		try {
			User user = userDAO.find(condition.getId());
			UserModel userModel = null;
			if (user != null) {
				userModel = new UserModel();
				BeanUtils.copyProperties(user, userModel);

				userModel.setTotalMicropost(micropostDAO.count(Restrictions.eq("userId", condition.getId())));
				userModel.setTotalFollowing(relationshipDAO.count(Restrictions.eq("followerId", condition.getId())));
				userModel.setTotalFollowers(relationshipDAO.count(Restrictions.eq("followedId", condition.getId())));
				
				if (condition.getCurrentUserId() != null) {
					Relationship relationship = new Relationship();
					relationship.setFollowerId(condition.getCurrentUserId());
					relationship.setFollowedId(condition.getId());
					userModel.setFollowedByCurrentUser(relationshipDAO.isFollowing(relationship));
				}
			}
			return userModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details from the database", e);
			return null;
		}
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserModel> following(UserModel userModel) {
		logger.info("Fetching all following in the database");
		try {
			User condition = new User();
			condition.setId(userModel.getId());
			Page<User> users = userDAO.following(condition, userModel.getPageable());
			return users.map(user -> {
				UserModel model = new UserModel();
				BeanUtils.copyProperties(user, model);
				return model;
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching all following from the database", e);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserModel> followers(UserModel userModel) {
		logger.info("Fetching all followers in the database");
		try {
			User condition = new User();
			condition.setId(userModel.getId());
			Page<User> users = userDAO.followers(condition, userModel.getPageable());
			return users.map(user -> {
				UserModel model = new UserModel();
				BeanUtils.copyProperties(user, model);
				return model;
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching all followers from the database", e);
			return null;
		}
	}
	
	@Override
	public boolean existingEmail(String email, Integer id) {
		logger.info("Checking the user by email in the database");
		try {
			return userDAO.existingEmail(email, id);
		} catch (Exception e) {
			logger.error("An error occurred while checking the user details by email from the database", e);
			return true;
		}
	}

	
	@Transactional
	public boolean deleteUser(UserModel userModel) throws Exception {
		logger.info("Deleting the user in the database");
		try {
			User user = userDAO.find(userModel.getId(), true);
			userDAO.makeTransient(user);
			return true;
		} catch (Exception e) {
			logger.error("An error occurred while adding the user details to the database", e);
			throw e;
		}
	}
	
	

	
}
