package dinhhieumvc.dao;

import java.util.List;

import dinhhieumvc.entity.Micropost;

public interface SearchMicropostDAO {
	public List<Micropost> findMicropostByKeyword(String keyword);
}
