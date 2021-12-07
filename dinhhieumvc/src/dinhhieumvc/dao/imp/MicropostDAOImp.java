package dinhhieumvc.dao.imp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.MicropostDAO;
import dinhhieumvc.entity.Micropost;
import dinhhieumvc.entity.Rating;
import dinhhieumvc.util.SearchQueryTemplate;

@Repository
public class MicropostDAOImp  extends GenericDAOImp<Micropost, Integer> implements MicropostDAO {

	public MicropostDAOImp() {
		super(Micropost.class);
	}

	@Override
	public Page<Micropost> paginate(Micropost micropost, Pageable pageable) {
		String sql = "FROM Micropost WHERE userId = :userId";
		String countSql = "SELECT COUNT(*) FROM Micropost WHERE userId = :userId";
		SearchQueryTemplate searchQueryTemplate = new SearchQueryTemplate(sql, countSql, pageable);
		searchQueryTemplate.addParameter("userId", micropost.getUserId());
		searchQueryTemplate.addOrder(Direction.DESC, "createdAt");
		return paginate(searchQueryTemplate);
	}
	
	public Double avgScore(Rating rating) {
		logger.info("Checking scoreRating in database");
		return getHibernateTemplate().execute(new HibernateCallback<Double>() {
			public Double doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT AVG(rateScore) FROM Rating WHERE micropostId = :micropostId";
				Query<Double> query = session.createQuery(sql, Double.class);
				query.setParameter("micropostId", rating.getMicropostId());
				return query.uniqueResult();
			}
		}) ;
	}

}
