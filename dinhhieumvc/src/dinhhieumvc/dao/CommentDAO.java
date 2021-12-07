package dinhhieumvc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dinhhieumvc.entity.Comment;

public interface CommentDAO extends GenericDAO<Comment, Integer>{
	public Page<Comment> paginate(Comment comment, Pageable pageable);
}
