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
            "SELECT USER_ID, PASS, MAIL, NAME, AGE FROM ACCOUNTS WHERE USER_ID=? AND PASS=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, loginData.getUserId());
            pStmt.setString(2, loginData.getPass());

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()){
                String userId = rs.getString("USER_ID");
                String pass = rs.getString("PASS");
                String mail = rs.getString("MAIL");
                String name = rs.getString("NAME");
                int age = rs.getInt("AGE");
                user = new User(userId, pass, mail, name, age);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}
