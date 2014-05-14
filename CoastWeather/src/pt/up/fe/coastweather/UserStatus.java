package pt.up.fe.coastweather;

import org.json.JSONException;
import org.json.JSONObject;

public class UserStatus {
	public final static int FLAG_GREEN = 0;
	public final static int FLAG_YELLOW = 1;
	public final static int FLAG_RED = 2;
	public final static int FLAG_BLACK = 3;
	
	private int beachId;
	private int beachName;

	private int feeling;

	private boolean sunny;
	private boolean windy;
	private boolean cloudy;
	private boolean rainy;

	private int flag;

	private long userID;
	private String username;

	private String date;
	
	//Only for ones in server
	private int id;

	UserStatus(String j) throws JSONException {
		JSONObject x = new JSONObject(j);
		
		id = x.getInt("IdStatus");
		userID = x.getLong("idUser");
		beachId = x.getInt("idBeach");
		feeling = x.getInt("feeling");
		flag = x.getInt("flag");
		sunny = x.getBoolean("sun");
		windy = x.getBoolean("wind");
		cloudy = x.getBoolean("clouds");
		rainy = x.getBoolean("rain");
		date = x.getString("date");
		
		
		
		
		
	}


}
