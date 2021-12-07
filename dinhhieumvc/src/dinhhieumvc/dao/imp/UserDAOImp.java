package dinhhieumvc.dao.imp;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.UserDAO;
import dinhhieumvc.entity.User;
import dinhhieumvc.util.SearchQueryTemplate;

@Repository
public class UserDAOImp extends GenericDAOImp<User, Integer> implements UserDAO {
	
	public UserDAOImp() {
		super(User.class);
	}
	
	public User findUserByEmail(String email) {
		logger.info("Finding the user by email in the database");
		try {
			return getHibernateTemplate().execute(new HibernateCallback<User>() {
				public User doInHibernate(Session session) throws HibernateException {
					Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
					query.setParameter("email", email);
					return query.uniqueResult();
				}
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}
	}
	
	public User findUserByUid(String uid) {
		logger.info("Finding the user by uid in the database");
		try {
			return getHibernateTemplate().execute(new HibernateCallback<User>() {
				public User doInHibernate(Session session) throws HibernateException {
					Query<User> query = session.createQuery("FROM User WHERE uid = :uid", User.class);
					query.setParameter("uid", uid);
					return query.uniqueResult();
				}
			});
		} catch (Exception e) {
			logger.error("An error occurred while fetching the user details by email from the database", e);
			return null;
		}
	}

	@Override
	public List<User> findAllUser() {
			return getHibernateTemplate().execute(new HibernateCallback<List<User>>() {
			public List<User> doInHibernate(Session session) throws HibernateException {
				Query<User> query = session.createQuery("FROM User", User.class);
				return query.getResultList();
			}
		});
	}
	
	@Override
	public Page<User> following(User user, Pageable pageable) {
		String sql = "SELECT following FROM User AS user INNER JOIN user.following AS following WHERE user.id = :userId";
		String countSql = "SELECT COUNT(*) FROM User AS user INNER JOIN user.following AS following WHERE user.id = :userId";
		SearchQueryTemplate searchQueryTemplate = new SearchQueryTemplate(sql, countSql, pageable);
		searchQueryTemplate.addParameter("userId", user.getId());
		searchQueryTemplate.addOrder(Direction.DESC, "user.createdAt");
		return paginate(searchQueryTemplate);
	}

	@Override
	public Page<User> followers(User user, Pageable pageable) {
		String sql = "SELECT followers FROM User AS user INNER JOIN user.followers AS followers WHERE user.id = :userId";
		String countSql = "SELECT COUNT(*) FROM User AS user INNER JOIN user.followers AS followers WHERE user.id = :userId";
		SearchQueryTemplate searchQueryTemplate = new SearchQueryTemplate(sql, countSql, pageable);
		searchQueryTemplate.addParameter("userId", user.getId());
		searchQueryTemplate.addOrder(Direction.DESC, "user.createdAt");
		return paginate(searchQueryTemplate);
	}
	
	public boolean existingEmail(String email, Integer id) {
		logger.info("Finding the user by email in the database");
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException {
				String sql = "SELECT COUNT(*) FROM User WHERE email = :email";
				if (id != null) {
					sql += " AND id <> :id";
				}
				Query<Long> query = session.createQuery(sql, Long.class);
				query.setParameter("email", email);
				if (id != null) {
					query.setParameter("id", id);
				}
				return query.uniqueResult();
			}
		}) > 0;
	}
		


}
