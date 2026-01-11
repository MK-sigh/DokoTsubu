package dokotsubu.model;

import dokotsubu.DAO.UserDAO;

public class RegisterUserLogic {
    //ユーザー情報の登録
    public boolean execute (User user){
        UserDAO dao = new UserDAO();
        return dao.registerUser(user);
    }
}
