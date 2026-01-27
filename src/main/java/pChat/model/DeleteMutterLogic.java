package pChat.model;

import pChat.DAO.MuttersDAO;

public class DeleteMutterLogic {
    public boolean execute(int id) {
        MuttersDAO dao = new MuttersDAO();
        return dao.delete(id);
    }
}
