package pt.up.fe.coastweather.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class UserStatus {
	public static final String LOG_TEST_MESSAGE = "TESTING_JSON";


	public final static int FLAG_GREEN = 0;
	public final static int FLAG_YELLOW = 1;
	public final static int FLAG_RED = 2;
	public final static int FLAG_BLACK = 3;

	private int beachId;
	private String beachName;
	private String place;

	private int feeling;

	private boolean sunny;
	private boolean windy;
	private boolean cloudy;
	private boolean rainy;

	private int flag;

	private long userID;
	private String username;

	private String date;

	private final String BEACH_ID = "idBeach";
	private final String BEACH_NAME = "beach";
	private final String FEELING = "feeling";
	private final String SUNNY = "sun";
	private final String WINDY = "wind";
	private final String CLOUDY = "clouds";
	private final String RAINY = "rain";
	private final String FLAG = "flag";
	private final String USER_ID = "idUser";
	private final String USERNAME = "name";
	private final String DATE = "date";
	private final String STATUS_ID = "idStatus";
	private final String PLACE = "place";

	//Only for ones in server
	private int statusId;

	public UserStatus(String j) throws JSONException {
		JSONObject x = new JSONObject(j);

		statusId = x.getInt(STATUS_ID);
		userID = x.getLong(USER_ID); 
		username = x.getString(USERNAME);
		beachId = x.getInt(BEACH_ID);
		beachName = x.getString(BEACH_NAME);
		place = x.isNull(PLACE) ? null : x.getString(PLACE);
		feeling = x.getInt(FEELING);
		flag = x.isNull(FLAG) ? -1 : x.getInt(FLAG);
		sunny = Boolean.parseBoolean(x.getString(SUNNY));
		windy =  Boolean.parseBoolean(x.getString(WINDY));
		cloudy =  Boolean.parseBoolean(x.getString(CLOUDY));
		rainy =  Boolean.parseBoolean(x.getString(RAINY));
		date = x.getString(DATE);

		Log.i(LOG_TEST_MESSAGE, "Success parsing!");
	}

	public UserStatus(JSONObject x) throws JSONException {
		statusId = x.getInt(STATUS_ID);
		userID = x.getLong(USER_ID);
		username = x.getString(USERNAME);
		beachId = x.getInt(BEACH_ID);
		beachName = x.getString(BEACH_NAME);
		place = x.isNull(PLACE) ? null : x.getString(PLACE);
		feeling = x.getInt(FEELING);
		flag = x.isNull(FLAG) ? -1 : x.getInt(FLAG);
		sunny = Boolean.parseBoolean(x.getString(SUNNY));
		windy =  Boolean.parseBoolean(x.getString(WINDY));
		cloudy =  Boolean.parseBoolean(x.getString(CLOUDY));
		rainy =  Boolean.parseBoolean(x.getString(RAINY));
		date = x.getString(DATE);

		Log.i(LOG_TEST_MESSAGE, "Success parsing!");
	}

	public UserStatus(int beachId, int feeling, int flag, boolean sunny, boolean windy, boolean cloudy, boolean rainy) {
		this.userID = Long.parseLong(User.getInstance().getFacebookId());
		this.beachId = beachId;
		this.feeling = feeling;
		this.flag = flag;
		this.sunny = sunny;
		this.windy = windy;
		this.cloudy = cloudy;
		this.rainy = rainy;
	}

	@Override
	public String toString() {
		return "UserStatus [beachId=" + beachId + ", beachName=" + beachName
				+ ", feeling=" + feeling + ", sunny=" + sunny + ", windy="
				+ windy + ", cloudy=" + cloudy + ", rainy=" + rainy + ", flag="
				+ flag + ", userID=" + userID + ", username=" + username
				+ ", date=" + date + ", statusId=" + statusId + "]";
	}

	public int getBeachId() {
		return beachId;
	}

	public void setBeachId(int beachId) {
		this.beachId = beachId;
	}

	public String getBeachName() {
		return beachName;
	}

	public void setBeachName(String beachName) {
		this.beachName = beachName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getFeeling() {
		return feeling;
	}

	public void setFeeling(int feeling) {
		this.feeling = feeling;
	}

	public boolean isSunny() {
		return sunny;
	}

	public void setSunny(boolean sunny) {
		this.sunny = sunny;
	}

	public boolean isWindy() {
		return windy;
	}

	public void setWindy(boolean windy) {
		this.windy = windy;
	}

	public boolean isCloudy() {
		return cloudy;
	}

	public void setCloudy(boolean cloudy) {
		this.cloudy = cloudy;
	}

	public boolean isRainy() {
		return rainy;
	}

	public void setRainy(boolean rainy) {
		this.rainy = rainy;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public String getDateFormatted() {

		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(date);
			d2 = new Date();

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			Log.w("CoastWeather", "statusId:"+statusId + "\ndiffSeconds=" + diffSeconds + "\ndiffMinutes" + diffMinutes + "\ndiffHours" + diffHours + "\ndiffDays");

			if(diffMinutes == 0)
				return diffSeconds + (diffSeconds == 1 ? " second " : " seconds ") + "ago"; 
			else if (diffHours == 0)
				return diffMinutes + (diffMinutes == 1 ? " minute " : " minutes ") + "ago"; 
			else if (diffDays == 0)
				return diffHours + (diffHours == 1 ? " hour " : " hours ") + "ago";
			else if (diffDays <= 7)
				return diffDays + (diffDays == 1 ? " day " : " days ") + "ago";
			else
				return date;


		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}



	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(USER_ID, userID); 
			json.put(BEACH_ID, beachId);
			json.put(FEELING, feeling);
			json.put(FLAG, flag);
			json.put(SUNNY, sunny);
			json.put(WINDY, windy);
			json.put(CLOUDY, cloudy);
			json.put(RAINY, rainy);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return json;
	}

	public List<NameValuePair> getPost() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(USER_ID, Long.toString(userID)));
		nameValuePairs.add(new BasicNameValuePair(BEACH_ID, Integer.toString(beachId)));
		nameValuePairs.add(new BasicNameValuePair(FEELING, Integer.toString(feeling)));
		nameValuePairs.add(new BasicNameValuePair(FLAG, Integer.toString(flag)));
		nameValuePairs.add(new BasicNameValuePair(SUNNY, Boolean.toString(sunny)));
		nameValuePairs.add(new BasicNameValuePair(WINDY, Boolean.toString(windy)));
		nameValuePairs.add(new BasicNameValuePair(CLOUDY, Boolean.toString(cloudy)));
		nameValuePairs.add(new BasicNameValuePair(RAINY, Boolean.toString(rainy)));
		return nameValuePairs;
	}
	

	//idUser, idBeach, content, feeling, flag, sun, wind, clouds, rain

}
