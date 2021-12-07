package dinhhieumvc.dao;
import java.util.List;

import dinhhieumvc.entity.Buys;
import dinhhieumvc.model.UserModel;

public interface BuyDAO extends GenericDAO<Buys, Integer> {
	public Buys isAddToCart(Integer userId, Integer microportId);
	public List<Buys> findAllBuyByUser (UserModel user);
}
