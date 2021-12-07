package dinhhieumvc.dao.imp;

import javax.persistence.LockModeType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.RelationshipDAO;
import dinhhieumvc.entity.Relationship;

@Repository
public class RelationshipDAOImp extends GenericDAOImp<Relationship, Integer> implements RelationshipDAO {

	public RelationshipDAOImp() {
		super(Relationship.class);
		// TODO Auto-generated constructor stub
	}
	
	public Relationship load(Relationship relationship) {
		logger.info("Fetching the relationship by follower_id and followed_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Relationship>() {
			public Relationship doInHibernate(Session session) throws HibernateException {
				String sql = "FROM Relationship WHERE followerId = :followerId AND followedId = :followedId";
				Query<Relationship> query = session.createQuery(sql, Relationship.class);
				query.setParameter("followerId", relationship.getFollowerId());
				query.setParameter("followedId", relationship.getFollowedId());
				query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
				return query.uniqueResult();
			}
		});
	}
	
	public boolean isFollowing(Relationship relationship) {
		logger.info("Checking the user followed by followed_id in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT COUNT(*) FROM Relationship WHERE followerId = :followerId AND followedId = :followedId";
				Query<Long> query = session.createQuery(sql, Long.class);
				query.setParameter("followerId", relationship.getFollowerId());
				query.setParameter("followedId", relationship.getFollowedId());
				return query.uniqueResult();
			}
		}) > 0;
	}
	
}
