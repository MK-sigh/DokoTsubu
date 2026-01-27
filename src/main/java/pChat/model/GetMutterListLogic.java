package pChat.model;

import java.util.List;

import pChat.DAO.MuttersDAO;

public class GetMutterListLogic {
	public List<Mutter> execute(){
		MuttersDAO dao = new MuttersDAO();
		List<Mutter> mutterList = dao.findAll();
		return mutterList;
	}

}
