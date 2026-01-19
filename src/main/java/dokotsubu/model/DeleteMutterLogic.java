package dokotsubu.model;

import dokotsubu.DAO.MuttersDAO;

public class DeleteMutterLogic {
    public void execute(int id) {
    MuttersDAO dao = new MuttersDAO();
    dao.delete(id);
}
}
