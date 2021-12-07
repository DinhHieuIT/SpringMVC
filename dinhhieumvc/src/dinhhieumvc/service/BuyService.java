package dinhhieumvc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dinhhieumvc.model.BuyModel;
import dinhhieumvc.model.UserModel;

public interface BuyService  {
	public BuyModel buy(Integer userId, Integer microportId, Integer value) throws Exception;
    public BuyModel findBuy(Integer id, HttpServletRequest request);
    public List<BuyModel> findAllBuyModel(UserModel user);
    public BuyModel buyRemove(Integer buyId, Integer value) throws Exception;
    public BuyModel buyAdd(Integer buyId, Integer value) throws Exception;
}
