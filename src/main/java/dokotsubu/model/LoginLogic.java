package dokotsubu.model;

import dokotsubu.DAO.UserDAO;

public class LoginLogic {
	// ユーザー情報の存在チェック
	public User findUser (User user) {
		UserDAO dao = new UserDAO();
		return dao.findUser(user);
	}
}
