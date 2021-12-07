package dinhhieumvc.dao.imp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.BuyDAO;
import dinhhieumvc.entity.Buys;
import dinhhieumvc.model.UserModel;

@Repository
public class BuyDAOImp extends GenericDAOImp<Buys, Integer> implements BuyDAO {

	public BuyDAOImp() {
		super(Buys.class);
		// TODO Auto-generated constructor stub
	}
	
	public Buys isAddToCart(Integer userId, Integer microportId) {
		logger.info("Checking buy in database");
		return getHibernateTemplate().execute(new HibernateCallback<Buys>() {
			public Buys doInHibernate(Session session) throws HibernateException {
				String sql = "FROM Buys WHERE userId = :userId AND micropostId = :micropostId";
				Query<Buys> query = session.createQuery(sql, Buys.class);
				query.setParameter("userId", userId);
				query.setParameter("micropostId", microportId);
				return query.uniqueResult();
			}
		});
	}
	
	public List<Buys> findAllBuyByUser (UserModel user){
		logger.info("Finding all buys by user in the database");
		try {
			return getHibernateTemplate().execute(new HibernateCallback<List<Buys>>() {
				public List<Buys> doInHibernate(Session session) throws HibernateException {
					Query<Buys> query = session.createQuery("FROM Buys WHERE userId = :userId", Buys.class);
					query.setParameter("userId",user.getId());
					return query.getResultList();
				}
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching all buys by user from the database", e);
			return null;
		}
	}

}
	


