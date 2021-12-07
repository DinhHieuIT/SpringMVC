package dinhhieumvc.dao.imp;
import javax.persistence.LockModeType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.LikeDAO;
import dinhhieumvc.entity.Likes;

@Repository
public class LikeDAOImp extends GenericDAOImp<Likes, Integer> implements LikeDAO {

	public LikeDAOImp() {
		super(Likes.class);
		// TODO Auto-generated constructor stub
	}
	
	public Likes load(Likes likes) {
		logger.info("Fetching the like by user_id and micropost_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Likes>() {
			public Likes doInHibernate(Session session) throws HibernateException {
				String sql = "FROM Likes WHERE userId = :userId AND micropostId = :micropostId";
				Query<Likes> query = session.createQuery(sql, Likes.class);
				query.setParameter("userId", likes.getUserId());
				query.setParameter("micropostId", likes.getMicropostId());
				query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
				return query.uniqueResult();
			}
		});
	}
	
	public boolean isLike(Likes likes) {
		logger.info("Checking the user followed by followed_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT COUNT(*) FROM Likes WHERE userId = :userId AND micropostId = :micropostId";
				Query<Long> query = session.createQuery(sql, Long.class);
				query.setParameter("userId", likes.getUserId());
				query.setParameter("micropostId", likes.getMicropostId());
				return query.uniqueResult();
			}
		}) > 0;
	}
	
}
