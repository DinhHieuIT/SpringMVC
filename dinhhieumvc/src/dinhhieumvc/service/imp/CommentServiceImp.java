package dinhhieumvc.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.CommentDAO;
import dinhhieumvc.entity.Comment;
import dinhhieumvc.model.CommentModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.CommentService;


@Service
public class CommentServiceImp implements CommentService {
	private static final Logger logger = LoggerFactory.getLogger(MicropostServiceImp.class);
	@Autowired
	CommentDAO commentDAO;
	
	
	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	@Transactional
	public CommentModel addComment(CommentModel commentModel) throws Exception {
		logger.info("Adding the micropost in the database");
		try {
			Comment comment = new Comment();
			comment.setMicropostId(commentModel.getMicropostId());
			comment.setUserId(commentModel.getUserId());
			comment.setContent(commentModel.getContent());
			Comment commentEntity = commentDAO.makePersistent(comment);
			commentModel = new CommentModel();
			BeanUtils.copyProperties(commentEntity, commentModel);
			return commentModel;
		} catch (Exception e) {
			logger.error("An error occurred while adding the micropost details to the database", e);
			throw e;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<CommentModel> paginate(CommentModel commentModel) {
		logger.info("Adding the micropost in the database");
		try {
			Comment comment1 = new Comment();
			comment1.setMicropostId(commentModel.getMicropostId());
			Page<Comment> comments = commentDAO.paginate(comment1, commentModel.getPageable());
			return comments.map(comment -> {
				CommentModel model = new CommentModel();
				BeanUtils.copyProperties(comment, model);
				UserModel userModel = new UserModel();
				BeanUtils.copyProperties(comment.getUser(), userModel);
				model.setUser(userModel);
				return model;
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}
