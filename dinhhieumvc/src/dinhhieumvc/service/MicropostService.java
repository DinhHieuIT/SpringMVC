package dinhhieumvc.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import dinhhieumvc.model.BookmarkModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;

public interface MicropostService {
	
	public Page<MicropostModel> paginate(MicropostModel micropostModel);
	public int count(MicropostModel micropostModel);
	public MicropostModel addMicropost(MicropostModel micropostModel) throws Exception;
	public MicropostModel findMicropost(Integer id, HttpServletRequest request);
	public boolean rating(UserModel user, MicropostModel micropost, Integer rateScore) throws Exception;
	public BookmarkModel bookmark(UserModel user, MicropostModel micropost) throws Exception;
	public Double getMicropostRatingScore(UserModel user, MicropostModel micropost);

}
