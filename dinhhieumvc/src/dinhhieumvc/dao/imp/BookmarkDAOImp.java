package dinhhieumvc.dao.imp;

import java.util.List;

import javax.persistence.LockModeType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import dinhhieumvc.dao.BookmarkDAO;
import dinhhieumvc.entity.Bookmark;

public class BookmarkDAOImp extends GenericDAOImp<Bookmark, Integer> implements BookmarkDAO {

	public BookmarkDAOImp() {
		super(Bookmark.class);
	}

	public boolean isBookmark(Bookmark bookmark) {
		logger.info("Checking bookmark in database");
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT COUNT(*) FROM Bookmark WHERE userId = :userId AND micropostId = :micropostId";
				Query<Long> query = session.createQuery(sql, Long.class);
				query.setParameter("userId", bookmark.getUserId());
				query.setParameter("micropostId", bookmark.getMicropostId());
				return query.uniqueResult();
			}
		}) > 0;
	}

	public List<Bookmark> findBookmark(Bookmark bookmark) {
		logger.info("Finding the bookmark list in the database");
		return getHibernateTemplate().execute(new HibernateCallback<List<Bookmark>>() {
			public List<Bookmark> doInHibernate(Session session) throws HibernateException {
				Query<Bookmark> query = session.createQuery("FROM Bookmark WHERE userId = :userId", Bookmark.class);
				query.setParameter("userId", bookmark.getUserId());
				return query.getResultList();
			}
		});
	}
	
	public Bookmark load(Bookmark bookmark) {
		logger.info("Fetching the like by user_id and micropost_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Bookmark>() {
			public Bookmark doInHibernate(Session session) throws HibernateException {
				String sql = "FROM Bookmark WHERE userId = :userId AND micropostId = :micropostId";
				Query<Bookmark> query = session.createQuery(sql, Bookmark.class);
				query.setParameter("userId", bookmark.getUserId());
				query.setParameter("micropostId", bookmark.getMicropostId());
				query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
				return query.uniqueResult();
			}
		});
	}

}
