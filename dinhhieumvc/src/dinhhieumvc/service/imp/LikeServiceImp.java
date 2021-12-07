package dinhhieumvc.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.LikeDAO;
import dinhhieumvc.entity.Likes;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.LikeService;

public class LikeServiceImp implements LikeService {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeServiceImp.class);
	
	@Autowired
	LikeDAO likeDAO;
	
	public void setLikeDAO(LikeDAO likeDAO) {
		this.likeDAO = likeDAO;
	}
	
	@Transactional
	public boolean like(UserModel user, MicropostModel micropost) throws Exception {
		Likes like = new Likes();
		like.setUserId(user.getId());
		like.setMicropostId(micropost.getId());
		if(likeDAO.isLike(like)) {
			return false;
		} else {
			likeDAO.makePersistent(like);
			return true;
		}
	}
	


}
