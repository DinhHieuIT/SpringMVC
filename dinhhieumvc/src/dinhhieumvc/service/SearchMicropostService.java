package dinhhieumvc.service;

import java.util.List;

import dinhhieumvc.model.MicropostModel;

public interface SearchMicropostService {
	public List<MicropostModel> findAllMicropostSearch(String keyword);
}
