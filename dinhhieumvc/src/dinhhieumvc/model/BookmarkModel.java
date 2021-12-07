package dinhhieumvc.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BookmarkModel extends BaseModel {
	private Integer id;
	private Integer userId;
	private Integer micropostId;
	
	@NotEmpty(message = "{micropost.validation.content.required}")
	@Size(max = 128, message = "{micropost.validation.content.length}")
	private Date createdAt;
	
	private MicropostModel micropost;
	private UserModel user;
	
	private boolean isBookmarkedByCurrentUser;
	
	public boolean isBookmarkedByCurrentUser() {
		return isBookmarkedByCurrentUser;
	}
	public void setBookmarkedByCurrentUser(boolean isBookmarkedByCurrentUser) {
		this.isBookmarkedByCurrentUser = isBookmarkedByCurrentUser;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMicropostId() {
		return micropostId;
	}
	public void setMicropostId(Integer micropostId) {
		this.micropostId = micropostId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public MicropostModel getMicropost() {
		return micropost;
	}
	public void setMicropost(MicropostModel micropost) {
		this.micropost = micropost;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}

}
