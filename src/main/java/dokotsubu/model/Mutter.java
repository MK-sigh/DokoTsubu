package dokotsubu.model;
import java.io.Serializable;
public class Mutter implements Serializable{
	private int id;
	private int userId;
	private String userName;
	private String text;
	
	
	public Mutter() {}
	
	public Mutter(int userId, String text) {
		this.userId = userId;
		this.text = text;
	}
	
	public Mutter(int id, String userName, String text) {
		this.id = id;
		this.userName = userName;
		this.text = text;
	}
	
	public Mutter(int id,  int userId, String userName, String text) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.text = text;
	}
	
	public int getId() { return id;}
	public int getUserId() { return userId; }
	public String getUserName() { return userName; }
	public String getText() { return text; }
	
	public void setId(int id) {this.id = id;}
	public void setUserId(int userId) {this.userId = userId;}
	public void setUserName(String userName) {this.userName = userName;}
	public void setText(String text) {this.text = text;}
}