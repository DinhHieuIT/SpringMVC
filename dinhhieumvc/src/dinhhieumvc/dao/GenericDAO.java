package dinhhieumvc.dao;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dinhhieumvc.entity.BaseEntity;

public interface GenericDAO<E extends BaseEntity, Id extends Serializable> {
	public E find(Id id);
	
	public E makePersistent(E entity) throws Exception;
	
	public Page<E> paginate(Pageable pageable);
	
	public E find(Id id, boolean lock) throws Exception;
	
	public int count(Criterion... criterion);
	
	public void makeTransient(E entity) throws Exception;
	
}
