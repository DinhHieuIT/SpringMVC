package dinhhieumvc.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dinhhieumvc.dao.BuyDAO;
import dinhhieumvc.dao.MicropostDAO;
import dinhhieumvc.entity.Buys;
import dinhhieumvc.model.BuyModel;
import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;
import dinhhieumvc.service.BuyService;

public class BuyServiceImp implements BuyService {

	private static final Logger logger = LoggerFactory.getLogger(BuyServiceImp.class);

	@Autowired
	MicropostDAO micropostDAO;

	@Autowired
	BuyDAO buyDAO;

	public void setBuyDAO(BuyDAO buyDAO) {
		this.buyDAO = buyDAO;
	}

	public void setMicropostDAO(MicropostDAO micropostDAO) {
		this.micropostDAO = micropostDAO;
	}

	@Transactional
	public BuyModel buy(Integer userId, Integer microportId, Integer value) throws Exception {
		try {
			Buys buy = buyDAO.isAddToCart(userId, microportId);
			if (buy != null) {
				buy.setQuantity(buy.getQuantity() + value);
			} else {
				buy = new Buys();
				buy.setUserId(userId);
				buy.setMicropostId(microportId);
				buy.setQuantity(value);
			}
			buy = buyDAO.makePersistent(buy);
			BuyModel buyModel = new BuyModel();
			BeanUtils.copyProperties(buy, buyModel);
			return buyModel;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
	
	@Transactional
	public BuyModel buyRemove(Integer buyId, Integer value) throws Exception {
		try {
			Buys buy = buyDAO.find(buyId);
			if (buy.getQuantity()>0) {
				buy.setQuantity(buy.getQuantity() - value);
			} 
			buy = buyDAO.makePersistent(buy);
			BuyModel buyModel = new BuyModel();
			BeanUtils.copyProperties(buy, buyModel);
			return buyModel;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
	
	@Transactional
	public BuyModel buyAdd(Integer buyId, Integer value) throws Exception {
		try {
			Buys buy = buyDAO.find(buyId);
			buy.setQuantity(buy.getQuantity() + value);
			buy = buyDAO.makePersistent(buy);
			BuyModel buyModel = new BuyModel();
			BeanUtils.copyProperties(buy, buyModel);
			return buyModel;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public BuyModel findBuy(Integer id, HttpServletRequest request) {
		logger.info("Checking the micropost in the database");
		try {
			Buys buys = buyDAO.find(id);
			BuyModel buyModel = null;
			buyModel = new BuyModel();
			BeanUtils.copyProperties(buys, buyModel);
			return buyModel;
		} catch (Exception e) {
			logger.error("An error occurred while fetching the micropost details from the database", e);
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public List<BuyModel> findAllBuyModel(UserModel user){
	
		logger.info("find Buy from Buys in the database");
		try {
			List<Buys> buys = buyDAO.findAllBuyByUser(user);
			List<BuyModel> buyModels = new ArrayList<BuyModel>();
			for (Buys buy : buys) {
				BuyModel buyModel = new BuyModel();
				BeanUtils.copyProperties(buy, buyModel);
				MicropostModel micropost = new MicropostModel();
				BeanUtils.copyProperties(buy.getMicropost(), micropost);
				buyModel.setMicropost(micropost);;
				buyModels.add(buyModel);
	        };
	        return buyModels;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	


}
