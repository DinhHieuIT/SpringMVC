package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.BookmarkDAO;
import dinhhieumvc.dao.UserDAO;
import dinhhieumvc.entity.Bookmark;
import dinhhieumvc.model.BookmarkModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.service.BookmarkService;

public class BookmarkServiceImp implements BookmarkService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookmarkServiceImp.class);
	

	@Autowired
	BookmarkDAO bookmarkDAO;
	
	@Autowired
	UserDAO userDAO;
	
	public void setBookmarkDAO(BookmarkDAO bookmarkDAO) {
		this.bookmarkDAO = bookmarkDAO;
	}
	

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional(readOnly = true)
	public List<BookmarkModel> findAllMicropostBookmark(BookmarkModel bookmarkModel){
	
		logger.info("find Micropost from Bookmark in the database");
		try {
			Bookmark bookmarkEntity = new Bookmark();
			bookmarkEntity.setUserId(bookmarkModel.getUserId());
			List<Bookmark> bookmarks = bookmarkDAO.findBookmark(bookmarkEntity);
			
			List<BookmarkModel> results = new ArrayList<BookmarkModel>();
			for (Bookmark bookmark : bookmarks) {
				BookmarkModel model = new BookmarkModel();
				BeanUtils.copyProperties(bookmark, model);
				MicropostModel micropost = new MicropostModel();
				BeanUtils.copyProperties(bookmark.getMicropost(), micropost);
				model.setMicropost(micropost);;
				results.add(model);
	        };
	        return results;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
