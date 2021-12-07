package dinhhieumvc.service;

import org.springframework.data.domain.Page;


import dinhhieumvc.model.CommentModel;

public interface CommentService {
	public CommentModel addComment(CommentModel commentModel) throws Exception;
	public Page<CommentModel> paginate(CommentModel commentModel);
}
