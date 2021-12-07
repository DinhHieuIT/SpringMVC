package dinhhieumvc.dao.imp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.GenericDAO;
import dinhhieumvc.entity.BaseEntity;
import dinhhieumvc.util.SearchQueryTemplate;
@Repository
public class GenericDAOImp <E extends BaseEntity,Id extends Serializable> extends HibernateDaoSupport
implements GenericDAO<E,Id>  {
	
	private Class<E> persistentClass;

	public GenericDAOImp(Class<E> persistentClass) {
		this.persistentClass = persistentClass;
	}
    
	public Class<E> getPersistentClass() {
		return persistentClass;
	}
	
	public Timestamp getSystemTimestamp() {
		String sql = "SELECT CURRENT_TIMESTAMP AS systemtimestamp";
		Object obj = getHibernateTemplate().execute(session -> session.createNativeQuery(sql).uniqueResult());
		Timestamp syatemTimestamp = null;
		if (obj != null) {
			syatemTimestamp = (Timestamp) obj;
		}
		return syatemTimestamp;
	}
	
	public E makePersistent(E entity) throws Exception {
		Date currentDate = getSystemTimestamp();
		if (entity.getCreatedAt() == null) {
			entity.setCreatedAt(currentDate);
		}
		entity.setUpdatedAt(currentDate);
		getHibernateTemplate().saveOrUpdate(entity);
		getHibernateTemplate().flush();
		return entity;
	}
	@Override
	@SuppressWarnings("unchecked")
	public E find(Id id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("id", id));
		return (E) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
	}

	
	protected Page<E> paginate(SearchQueryTemplate searchQueryTemplate) {
		List<E> results = getHibernateTemplate().execute(new HibernateCallback<List<E>>() {
			public List<E> doInHibernate(Session session) {	
				Query<E> query = session.createQuery(searchQueryTemplate.getSql(true), getPersistentClass());
				searchQueryTemplate.setPageable(query);
				searchQueryTemplate.setParameters(query);
	
				return query.list();
			}
		});
	
		Long count = getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) {
				Query<Long> query = session.createQuery(searchQueryTemplate.getCountSql(), Long.class);
				searchQueryTemplate.setParameters(query);
				return query.uniqueResult();
			}
		});

		return wrapResult(results, searchQueryTemplate.getPageable(), count);
	}

	private Page<E> wrapResult(List<E> results, Pageable page, long count) {
		if (results == null) {
			results = Collections.emptyList();
		}
		return new PageImpl<>(results, page, count);
	}
	
	public Page<E> paginate(Pageable pageable) {
		String sql = "FROM " + getPersistentClass().getName();
		String countSql = "SELECT COUNT(*) FROM " + getPersistentClass().getName();
		return paginate(new SearchQueryTemplate(sql, countSql, pageable));
	}
	
	public E find(Id id, boolean lock) throws Exception {
		if (lock) {
			return getHibernateTemplate().load(getPersistentClass(), id, LockMode.PESSIMISTIC_WRITE);
		} else {
			return getHibernateTemplate().get(getPersistentClass(), id);
		}

	}
	
	public int count(Criterion... criterion) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		return DataAccessUtils
				.intResult(getHibernateTemplate().findByCriteria(criteria.setProjection(Projections.rowCount())));
	}
	

	public void makeTransient(E entity) throws Exception {
		getHibernateTemplate().delete(entity);
		getHibernateTemplate().flush();
	}
	
}
