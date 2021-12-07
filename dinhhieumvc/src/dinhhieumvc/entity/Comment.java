package dinhhieumvc.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Comment extends BaseEntity implements Serializable {

	private Integer id;
	private Integer userId;
	private Integer micropostId;
	private String content;
	private User user;
	public Micropost micropost;
	
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMicropostId() {
		return micropostId;
	}
	public void setMicropostId(Integer micropostId) {
		this.micropostId = micropostId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
