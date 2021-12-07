package dinhhieumvc.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Likes extends BaseEntity implements Serializable {

	private Integer id;
	private Integer userId;
	private Integer micropostId;

	private User user;
	private Micropost micropost;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Micropost getMicropost() {
		return micropost;
	}
	public void setMicropost(Micropost micropost) {
		this.micropost = micropost;
	}

	
}
