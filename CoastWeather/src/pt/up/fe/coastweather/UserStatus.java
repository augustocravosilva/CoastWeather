package pt.up.fe.coastweather;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class UserStatus {
	@Override
	public String toString() {
		return "UserStatus [beachId=" + beachId + ", beachName=" + beachName
				+ ", feeling=" + feeling + ", sunny=" + sunny + ", windy="
				+ windy + ", cloudy=" + cloudy + ", rainy=" + rainy + ", flag="
				+ flag + ", userID=" + userID + ", username=" + username
				+ ", date=" + date + ", id=" + id + "]";
	}

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
