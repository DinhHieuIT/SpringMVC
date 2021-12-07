package dinhhieumvc.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import dinhhieumvc.entity.Micropost;

public class BuyModel extends BaseModel {
	private Integer id;
	private Integer userId;
	private Integer micropostId;
	
	private Integer quantity;
	
	@NotEmpty(message = "{micropost.validation.content.required}")
	@Size(max = 128, message = "{micropost.validation.content.length}")
	private Date createdAt;
	
	private MicropostModel micropost;
	private UserModel user;
	
	List<Micropost> microposts;
	
	public List<Micropost> getMicroposts() {
		return microposts;
	}
	public void setMicroposts(List<Micropost> microposts) {
		this.microposts = microposts;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
