package dokotsubu.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dokotsubu.model.Mutter;
import dokotsubu.util.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MuttersDAO {
	private static final Logger logger = LoggerFactory.getLogger(MuttersDAO.class);

	//全投稿取得
	public List<Mutter> findAll(){
		List<Mutter> mutterList = new ArrayList<Mutter>();
		//データベース接続
		try (Connection conn = DBManager.getConnection()){
			
			//SELECT文の準備
			String sql =
			"""
			SELECT m.id, a.name, m.text FROM mutters m
			JOIN accounts a ON (a.id = m.user_id)
			ORDER BY m.id DESC;
			""";

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
			logger.error("つぶやき検索処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}
		return mutterList;
	}
	
	//新規投稿の追加
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
			logger.error("つぶやき投稿処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}
		return true;
	}

	//検索機能
	public List<Mutter> search(String keyword){
		List<Mutter> mutterList = new ArrayList<Mutter>();
		try(Connection conn = DBManager.getConnection()){
			String sql =
				"""
				SELECT m.id, a.name ,m.text FROM mutters m
				JOIN accounts a ON (a.id = m.user_id)
				WHERE m.text LIKE ?
				ORDER BY m.id DESC;
				""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%"+keyword+"%");
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
		}catch(SQLException e){
			logger.error("つぶやき検索処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}return mutterList;
	}
	
	//編集（アップデート）機能
	public boolean update(int id, String text){
		try(Connection conn = DBManager.getConnection()){
			String sql =
				"""
				UPDATE mutters 
				SET text = ? 
				WHERE id = ?
				""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, text);
			pStmt.setInt(2, id);
			//INSERT文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch (SQLException e) {
			logger.error("つぶやき編集処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}
		return true;
	}

	//idから検索してつぶやきを返す機能
	public Mutter findById(int id){
		Mutter mutter = new Mutter();
		try(Connection conn = DBManager.getConnection()){
			String sql =
				"""
				SELECT m.id, m.user_id, a.name, m.text FROM mutters m
				JOIN accounts a ON (a.id = m.user_id)
				WHERE m.id = ?
				;
				""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			//SELECT文の結果をmutterに格納
			if (rs.next()){
				mutter.setId(rs.getInt("ID"));
				mutter.setUserId(rs.getInt("USER_ID"));
				mutter.setUserName(rs.getString("NAME"));
				mutter.setText(rs.getString("TEXT"));
			}
		}catch(SQLException e){
			logger.error("つぶやき検索処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}return mutter;
	}

	//削除機能
	public boolean delete(int id){
		try(Connection conn = DBManager.getConnection()){
			String sql =
				"""
				DELETE FROM mutters
				WHERE id = ?
				""";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch (SQLException e) {
			logger.error("つぶやき削除処理でDBエラーが発生しました", e);
			throw new RuntimeException("DB_ERROR");
		}
		return true;
	}
}
