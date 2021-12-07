package dinhhieumvc.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dinhhieumvc.entity.User;

public interface UserDAO extends GenericDAO<User,Integer>{
	
	public User findUserByEmail(String email);
	public User findUserByUid(String uid);
	
	public List<User> findAllUser();
	
    public Page<User> following(User user, Pageable pageable);
	
	public Page<User> followers(User user, Pageable pageable);
	
	public boolean existingEmail(String email, Integer id);
	
	

}
