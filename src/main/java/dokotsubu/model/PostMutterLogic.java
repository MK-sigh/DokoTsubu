package dokotsubu.model;
import dokotsubu.DAO.MuttersDAO;

public class PostMutterLogic {
	public void execute( Mutter mutter) {
		MuttersDAO dao = new MuttersDAO();
		dao.create(mutter);
	}

}
