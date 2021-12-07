package dinhhieumvc.service;

import java.util.List;

import dinhhieumvc.model.BookmarkModel;


public interface BookmarkService  {
	public List<BookmarkModel> findAllMicropostBookmark(BookmarkModel bookmarkModel);
}