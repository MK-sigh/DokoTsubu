package dokotsubu.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dokotsubu.model.User;
import dokotsubu.util.DBManager;

public class UserDAO {
    //ユーザー登録
    public boolean resisterUser(User user){
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //ユーザー情報の存在チェック
    public User findUser(User user){
            try(Connection conn = DBManager.getConnection()){
                String sql = "SELECT* FROM ACCOUNTS WHERE NAME=? AND PASS=?";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, user.getName());
                pStmt.setString(2, user.getPass());

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()){
                int userId = rs.getInt("USER_ID");
                String pass = rs.getString("PASS");
                String name = rs.getString("NAME");
                user = new User(userId, pass, name);
                return user;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
