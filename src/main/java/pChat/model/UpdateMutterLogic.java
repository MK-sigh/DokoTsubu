package pChat.model;

import pChat.DAO.MuttersDAO;

public class UpdateMutterLogic {
    public boolean execute(int id, String text) {
        MuttersDAO dao = new MuttersDAO();
        return dao.update(id, text);
	}
}
