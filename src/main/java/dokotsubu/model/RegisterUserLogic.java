package dokotsubu.model;

import dokotsubu.DAO.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterUserLogic {
    //ユーザー情報の登録
    public boolean execute (User user){
        UserDAO dao = new UserDAO();
            // パスワードをハッシュ化
        String hashed = BCrypt.hashpw(user.getPass(), BCrypt.gensalt());
        user.setPass(hashed);
        return dao.registerUser(user);
    }
}
