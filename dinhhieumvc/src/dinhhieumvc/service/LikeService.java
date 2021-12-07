package dinhhieumvc.service;

import dinhhieumvc.model.MicropostModel;
import dinhhieumvc.model.UserModel;

public interface LikeService  {
    public boolean like(UserModel user, MicropostModel micropost) throws Exception;
}
