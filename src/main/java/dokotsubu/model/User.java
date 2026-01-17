package dokotsubu.model;
import java.io.Serializable;

public class User implements Serializable{
	private int id;
	private String pass;
    private String name;

	public User(){}
    //存在チェック用
    public User (String pass,String name){
        this.pass = pass;
        this.name = name;
    }
    //フルデータ用
    public User (int id, String pass, String name){
        this.id = id;
        this.pass = pass;
        this.name = name;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getPass(){return pass;}
    public void setPass(String pass){this.pass=pass;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
}
