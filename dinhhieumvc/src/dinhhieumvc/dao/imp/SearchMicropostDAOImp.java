package dinhhieumvc.dao.imp;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;

import dinhhieumvc.dao.SearchMicropostDAO;
import dinhhieumvc.entity.Micropost;

public class SearchMicropostDAOImp extends GenericDAOImp<Micropost, Integer> implements SearchMicropostDAO {

	public SearchMicropostDAOImp() {
		super(Micropost.class);
	}
	
	public List<Micropost> findMicropostByKeyword(String keyword) {
		logger.info("Finding the micropost list in the database");
		return getHibernateTemplate().execute(new HibernateCallback<List<Micropost>>() {
			public List<Micropost> doInHibernate(Session session) throws HibernateException {
				Query<Micropost> query = session.createQuery("FROM Micropost WHERE content LIKE :keyword", Micropost.class);
				query.setParameter("keyword", '%'+keyword+'%');
				return query.getResultList();
			}
		});
	}
	
	
	

}
