package dinhhieumvc.dao.imp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import dinhhieumvc.dao.CommentDAO;
import dinhhieumvc.entity.Comment;
import dinhhieumvc.util.SearchQueryTemplate;

@Repository
public class CommentDAOImp extends GenericDAOImp<Comment, Integer> implements CommentDAO  {

	public CommentDAOImp() {
		super(Comment.class);
	}

	@Override
	public Page<Comment> paginate(Comment comment, Pageable pageable) {
		String sql = "FROM Comment WHERE micropostId = :micropostId";
		String countSql = "SELECT COUNT(*) FROM Comment WHERE micropostId = :micropostId";
		SearchQueryTemplate searchQueryTemplate = new SearchQueryTemplate(sql, countSql, pageable);
		searchQueryTemplate.addParameter("micropostId", comment.getMicropostId());
		searchQueryTemplate.addOrder(Direction.DESC, "createdAt");
		return paginate(searchQueryTemplate);
	}
}
