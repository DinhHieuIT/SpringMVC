package dinhhieumvc.dao;
import dinhhieumvc.entity.Likes;

public interface LikeDAO extends GenericDAO<Likes, Integer> {
	public boolean isLike(Likes like);
	public Likes load(Likes like);
}
