package dokotsubu.model;
import java.io.Serializable;

public class User implements Serializable{
	private int userId;
	private String pass;
    private String name;

	public User(){}
    //存在チェック用
    public User (String pass,String name){
        this.pass = pass;
        this.name = name;
    }
    //フルデータ用
    public User (int userId, String pass, String name){
        this.userId = userId;
        this.pass = pass;
        this.name = name;
    }

    public int getUserId(){return userId;}
    public void setUserId(int id){this.userId=id;}
    public String getPass(){return pass;}
    public void setPass(String pass){this.pass=pass;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
}
