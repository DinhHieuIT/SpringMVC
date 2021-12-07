package dinhhieumvc.dao.imp;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import dinhhieumvc.dao.RatingDAO;
import dinhhieumvc.entity.Rating;

public class RatingDAOImp extends GenericDAOImp<Rating, Integer> implements RatingDAO {
	
	public RatingDAOImp() {
		super(Rating.class);
	}
	
	public boolean isRating(Rating rating) {
		logger.info("Checking the user rated by rater_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT COUNT(*) FROM Rating WHERE userId = :userId AND micropostId = :micropostId";
				Query<Long> query = session.createQuery(sql, Long.class);
				query.setParameter("userId", rating.getUserId());
				query.setParameter("micropostId", rating.getMicropostId());
				return query.uniqueResult();
			}
		}) > 0;
	}
	
	public List<Rating> findRating(Rating rating) {
		logger.info("Finding the user by micropost in the database");
		try {
			return getHibernateTemplate().execute(new HibernateCallback<List<Rating>>() {
				public List<Rating> doInHibernate(Session session) throws HibernateException {
					Query<Rating> query = session.createQuery("FROM Rating WHERE micropostId = :micropostId", Rating.class);
					query.setParameter("micropostId",rating.getMicropostId());
					return query.getResultList();
				}
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by micropost from the database", e);
			return null;
		}
	}
	
	
}
