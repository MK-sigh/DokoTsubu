package dokotsubu.model;
import dokotsubu.DAO.MuttersDAO;

public class PostMutterLogic {
	public boolean execute( Mutter mutter) {
		MuttersDAO dao = new MuttersDAO();
		return dao.create(mutter);
	}

}
