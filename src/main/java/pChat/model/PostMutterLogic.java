package pChat.model;
import pChat.DAO.MuttersDAO;

public class PostMutterLogic {
	public boolean execute( Mutter mutter) {
		MuttersDAO dao = new MuttersDAO();
		return dao.create(mutter);
	}

}
