package dinhhieumvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import dinhhieumvc.model.UserModel;

public interface UserService extends UserDetailsService{

	public UserModel addUser(UserModel user) throws Exception;
	public UserModel addUserFacebook(UserModel user) throws Exception;
	
	public UserModel findUserByEmail(String email);
	public UserModel findUserByUid(String uid);
	
	public List<UserModel> findAllUser();
	
	public Page<UserModel> paginate(UserModel userModel);
	
	public UserModel findUser(Integer id);
	
	public UserModel editUser(UserModel userModel) throws Exception;
	
	public boolean follow(UserModel follower, UserModel followed) throws Exception;
	
	public boolean unfollow(UserModel follower, UserModel followed);
	
	public UserModel getUserInfo(UserModel condition);
	
	public Page<UserModel> following(UserModel userModel);

	public Page<UserModel> followers(UserModel userModel);
	
	public boolean existingEmail(String email, Integer id);
	
	public boolean deleteUser(UserModel userModel) throws Exception;
	
	
}
