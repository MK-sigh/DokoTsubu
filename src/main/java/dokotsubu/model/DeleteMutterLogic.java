package dokotsubu.model;

import dokotsubu.DAO.MuttersDAO;

public class DeleteMutterLogic {
    public boolean execute(int id) {
        MuttersDAO dao = new MuttersDAO();
        return dao.delete(id);
    }
}
