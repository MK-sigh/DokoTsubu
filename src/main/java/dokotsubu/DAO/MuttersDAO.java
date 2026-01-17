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
			"""
			SELECT m.id, a.name ,m.text FROM mutters m
			JOIN accouts a ON (a.id = m.user_id)
			ORDER BY m.id DESC";
			""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			//SELECT文の結果をArrayListに格納
			while (rs.next()){
				int id = rs.getInt("ID");
				String userName = rs.getString("USER_NAME");
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
			String sql = "INSERT INTO MUTTERS (USER_ID, TEXT) VALUES(?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//INSERT文の？に使用する値を設定してSQL文を完成
			pStmt.setInt(1, mutter.getUserId());
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

	public List<Mutter> searcMutter(String keyword){
		List<Mutter> mutterList = new ArrayList<Mutter>();
		try(Connection conn = DBManager.getConnection()){
			String sql =
				"""
				SELECT m.id, a.name ,m.text FROM mutters m
				JOIN accouts a ON (a.id = m.user_id)
				WHERE LIKE '%?%'
				ORDER BY m.id DESC";
				""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, keyword);
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			//SELECT文の結果をArrayListに格納
			while (rs.next()){
				int id = rs.getInt("ID");
				String userName = rs.getString("USER_NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id,userName,text);
				mutterList.add(mutter);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}return mutterList;
	}

}
