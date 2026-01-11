package dokotsubu.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dokotsubu.model.Mutter;
import dokotsubu.util.DBManager;

public class MuttersDAO {
	//データベース接続に関する情報

	public List<Mutter> findAll(){
		List<Mutter> mutterList = new ArrayList<Mutter>();
		//データベース接続
		try (Connection conn = DBManager.getConnection()){
			
			//SELECT文の準備
			String sql = 
					"SELECT ID, NAME, TEXT FROM MUTTERS ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			//SELECT文の結果をArrayListに格納
			while (rs.next()){
				int id = rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id,userName,text);
				mutterList.add(mutter);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}
	
	public boolean create (Mutter mutter) {
		//データベース接続
		try(Connection conn = DBManager.getConnection()){

			//INSERT文の準備（idは自動連番なので指定しなくてよい）
			String sql = "INSERT INTO MUTTERS (NAME, TEXT) VALUES(?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文の？に使用する値を設定してSQL文を完成
			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());
			
			//INSERT文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
