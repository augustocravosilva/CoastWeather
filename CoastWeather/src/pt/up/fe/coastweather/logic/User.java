package pt.up.fe.coastweather.logic;

import java.util.ArrayList;

import com.facebook.Session;

public class User {

	static private User myUser;
	
	private String name;
	private String facebookId;
	private ArrayList<User> friends;

	private boolean errorflag;

	private String email;

	private Session fbsession;
	
	private User() {
		friends = new ArrayList<User>();
		name = "";
		facebookId = "";
		errorflag = false;
		email = "";
	}
	
	private User(String fid, String fname) {
		this();
		name = fname;
		facebookId=fid;
	}
	
	static public synchronized User getInstance()
	{
		if(myUser==null)
			myUser = new User();
		
		return myUser;
	}
	
	static public synchronized void reset()
	{
		myUser = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public void addFriend(String fid, String fname) {
		friends.add(new User(fid,fname));
	}
	
	public boolean isLoggedIn()
	{
		return !errorflag && (facebookId!="");
	}
	
	public String friendsArrayAsString()
	{
		StringBuilder str = new StringBuilder();
		str.append("%5B");
		for(int i = 0; i < friends.size(); i++)
		{
			str.append("%22");
			str.append(friends.get(i).getFacebookId());
			str.append("%22");
			str.append(",");
		}
		str.append("%22");
		str.append(facebookId);
		str.append("%22");
		str.append("%5D");
		return str.toString();
	}
	
	public ArrayList<User> getFriends()
	{
		return friends;
	}
	
	public String getUserPicLink()
	{
		return "http://graph.facebook.com/"+facebookId+"/picture?type=square";
	}
	public String getUserPicLinkMedium()
	{
		return "http://graph.facebook.com/"+facebookId+"/picture?type=normal";
	}
	
	static public String getUserPicLink(String id)
	{
		return "http://graph.facebook.com/"+id+"/picture?type=square";
	}
	
	static public String getUserPicLinkMedium(String id)
	{
		return "http://graph.facebook.com/"+id+"/picture?type=normal";
	}

	public void setErrorFlag() {
		errorflag = true;
	}
	
	public void dismissErrorFlag() {
		errorflag = false;
	}

	public void setEmail(String emailstr) {
		email = emailstr;
	}

	public String getEmail() {
		return email;
	}

	public void setfbSession(Session msession) {
		fbsession = msession;
	}
	
	public Session getfbSession()
	{
		return fbsession;
	}

}
