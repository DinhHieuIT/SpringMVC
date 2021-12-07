package dinhhieumvc.dao;

import java.util.List;

import dinhhieumvc.entity.Bookmark;

public interface BookmarkDAO extends GenericDAO<Bookmark, Integer> {
	public boolean isBookmark(Bookmark bookmark);
	public List<Bookmark> findBookmark(Bookmark bookmark);
	public Bookmark load(Bookmark bookmark);
}
