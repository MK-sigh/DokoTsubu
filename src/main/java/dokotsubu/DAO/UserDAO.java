package dokotsubu.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dokotsubu.model.User;
import dokotsubu.util.DBManager;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
    private static final Logger logger =LoggerFactory.getLogger(UserDAO.class);
    //ユーザー登録
    public boolean registerUser(User user){
        try(Connection conn = DBManager.getConnection()){
            String sql =
            "INSERT INTO ACCOUNTS (NAME, PASS) VALUES (?,?);";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());

            int result = pStmt.executeUpdate();
            if (result !=1){
                return false;
            }
        }catch (SQLException e){
            logger.error("ユーザー登録処理でDBエラーが発生しました", e);
            throw new RuntimeException("DB_ERROR");
        }
        return true;
    }

    //ユーザー情報の存在チェック
    public User findUser(User user){
            try(Connection conn = DBManager.getConnection()){
                String sql = "SELECT* FROM ACCOUNTS WHERE NAME=?";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, user.getName());

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()){
                String dbPass = rs.getString("PASS");
                    if(BCrypt.checkpw(user.getPass(), dbPass)){
                        int userId = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        user = new User(userId, name);
                        return user;
                    }
            }
            return null;
        }catch(SQLException e){
            logger.error("ユーザー検索処理でDBエラーが発生しました", e);
            throw new RuntimeException("DB_ERROR");
        }
    }
}
