package pChat.model;

import pChat.DAO.UserDAO;

public class LoginLogic {
	// ユーザー情報の存在チェック
	public User find (User user) {
		UserDAO dao = new UserDAO();
		return dao.findUser(user);
	}
}
