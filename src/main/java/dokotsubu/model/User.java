package dokotsubu.model;
import java.io.Serializable;

public class User implements Serializable{
	private int id;
    private String name;
	private String pass;

	public User(){}
    //存在チェック用
    public User (String name, String pass){
        this.name = name;
        this.pass = pass;
    }
    //id.name
    public User (int id, String name){
        this.id = id;
        this.name = name;
    }
    //フルデータ用
    public User (int id, String name, String pass){
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getPass(){return pass;}
    public void setPass(String pass){this.pass=pass;}
}
