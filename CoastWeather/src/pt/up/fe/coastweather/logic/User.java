package pt.up.fe.coastweather.logic;

import java.util.ArrayList;

public class User {

	static private User myUser;
	
	private String name;
	private String facebookId;
	private ArrayList<User> friends;
	
	private User() {
		friends = new ArrayList<User>();
		name = "";
		facebookId = "";
	}
	
	private User(String fid, String fname) {
		name = fname;
		facebookId=fid;
	}
	
	static public synchronized User getInstance()
	{
		if(myUser==null)
			myUser = new User();
		
		return myUser;
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
		return (facebookId!="");
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
	
	static public String getUserPicLink(String id)
	{
		return "http://graph.facebook.com/"+id+"/picture?type=square";
	}

}
