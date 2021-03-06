package dinhhieumvc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
public class User extends BaseEntity implements Serializable {

	private Integer id;
	private String uid;
	private String name;
	private String email;
	private String password;
	private String series;
	private String token;
	private Date lastUsed;
	private Integer role;
	
	private List<Micropost> microposts;
	private List<Comment> comments;
	
	private List<Relationship> activeRelationships;
	private List<Relationship> passiveRelationships;
	
	private List<User> following;
	private List<User> followers;
	
	private List<User> user;
	private List<Micropost> micropost;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<Micropost> getMicropost() {
		return micropost;
	}

	public void setMicropost(List<Micropost> micropost) {
		this.micropost = micropost;
	}
	
	
	public List<Relationship> getActiveRelationships() {
		return activeRelationships;
	}

	public void setActiveRelationships(List<Relationship> activeRelationships) {
		this.activeRelationships = activeRelationships;
	}

	public List<Relationship> getPassiveRelationships() {
		return passiveRelationships;
	}

	public void setPassiveRelationships(List<Relationship> passiveRelationships) {
		this.passiveRelationships = passiveRelationships;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Micropost> getMicroposts() {
		return microposts;
	}

	public void setMicroposts(List<Micropost> microposts) {
		this.microposts = microposts;
	}

	public User() {

	}

	public User(Integer id) {
		this.id = id;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
