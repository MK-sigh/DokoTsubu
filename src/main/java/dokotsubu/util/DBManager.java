package dokotsubu.util;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private static Properties props = new Properties();
    static {
        // クラスロード時に一度だけファイルを読み込む
        try (InputStream is =
            DBManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new RuntimeException("db.propertiesが見つかりません");
            }
            props.load(is);
            try{
            Class.forName(props.getProperty("driver"));
            }catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCが読み込めませんでした");}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
            props.getProperty("url"),
            props.getProperty("user"),
            props.getProperty("pass")
        );
    }
}
