package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.SearchMicropostDAO;
import dinhhieumvc.entity.Micropost;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.service.SearchMicropostService;

public class SearchMicropostServiceImp implements SearchMicropostService {
	private static final Logger logger = LoggerFactory.getLogger(SearchMicropostServiceImp.class);
	
	@Autowired
	SearchMicropostDAO searchmicropostDAO;
	
	public void setSearchmicropostDAO(SearchMicropostDAO searchmicropostDAO) {
		this.searchmicropostDAO = searchmicropostDAO;
	}


	@Transactional(readOnly = true)
	public List<MicropostModel> findAllMicropostSearch(String keyword){
	
		logger.info("find Micropost with given keyword from Micropost in the database");
		try {
			List<Micropost> microposts = searchmicropostDAO.findMicropostByKeyword(keyword);
			List<MicropostModel> micropostSearchs = new ArrayList<MicropostModel>();
			for (Micropost micropost : microposts) {
				MicropostModel model = new MicropostModel();
				BeanUtils.copyProperties(micropost, model);
				micropostSearchs.add(model);
	        };
	        return micropostSearchs;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	
	
	
}
