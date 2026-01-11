package dokotsubu.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dokotsubu.model.LoginData;
import dokotsubu.model.User;
import dokotsubu.util.DBManager;

public class UserDAO {
    public User findByLogin(LoginData loginData){
        User user = null;
        try(Connection conn = DBManager.getConnection()){
            String sql =
            "SELECT USER_ID, PASS, NAME,  FROM ACCOUNTS WHERE USER_ID=? AND PASS=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, loginData.getUserId());
            pStmt.setString(2, loginData.getPass());

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()){
                int userId = rs.getInt("USER_ID");
                String pass = rs.getString("PASS");
                String name = rs.getString("NAME");
                user = new User(userId, pass, name);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}
