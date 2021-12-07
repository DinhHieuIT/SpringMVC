package dinhhieumvc.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MicropostModel extends BaseModel {
	private Integer id;
	private Integer userId;
	@NotEmpty(message = "{micropost.validation.content.required}")
	@Size(max = 128, message = "{micropost.validation.content.length}")
	private String content;
	private Date createdAt;
	private Double totalRating = 0.0;
	private Integer micropostRating=0;
	private Integer totalBuyOneMicropost=0;
	
	private UserModel user;
    @JsonIgnore
	List<CommentModel> comments;
    @JsonIgnore
	List<RatingModel> ratings;
    
    @JsonIgnore
	List<LikeModel> likes;
    
   
    
    private boolean bookmarkByCurrentUser = false;
    
	public boolean isBookmarkByCurrentUser() {
		return bookmarkByCurrentUser;
	}

	public void setBookmarkByCurrentUser(boolean bookmarkByCurrentUser) {
		this.bookmarkByCurrentUser = bookmarkByCurrentUser;
	}
	 boolean likeByCurrentUser = false;
	public boolean isLikeByCurrentUser() {
		return likeByCurrentUser;
	}

	public void setLikeByCurrentUser(boolean likeByCurrentUser) {
		this.likeByCurrentUser = likeByCurrentUser;
	}

	public List<LikeModel> getLikes() {
		return likes;
	}

	public void setLikes(List<LikeModel> likes) {
		this.likes = likes;
	}

	
	public List<RatingModel> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingModel> ratings) {
		this.ratings = ratings;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Double getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Double totalRating) {
		this.totalRating = totalRating;
	}

	public Integer getMicropostRating() {
		return micropostRating;
	}

	public void setMicropostRating(Integer micropostRating) {
		this.micropostRating = micropostRating;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}
	
	public Integer getTotalBuyOneMicropost() {
		return totalBuyOneMicropost;
	}

	public void setTotalBuyOneMicropost(Integer totalBuyOneMicropost) {
		this.totalBuyOneMicropost = totalBuyOneMicropost;
	}

	
}
