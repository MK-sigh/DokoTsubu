package dokotsubu.model;

import dokotsubu.DAO.MuttersDAO;

public class UpdateMutterLogic {
    public void execute(int id, String text) {
        MuttersDAO dao = new MuttersDAO();
        dao.update(id, text);
	}
}
