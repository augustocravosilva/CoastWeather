package pt.up.fe.coastweather;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.model.GraphUser;

import android.util.Log;

public class UserStatus {
	public static final String LOG_TEST_MESSAGE = "TESTING_JSON";


	public final static int FLAG_GREEN = 0;
	public final static int FLAG_YELLOW = 1;
	public final static int FLAG_RED = 2;
	public final static int FLAG_BLACK = 3;

	private int beachId;
	private String beachName;
	private String place; //TODO: database add

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

	UserStatus(String j) throws JSONException {
		JSONObject x = new JSONObject(j);

		statusId = x.getInt(STATUS_ID);
		//userID = x.getLong(USER_ID); //TODO: DATABASE correction
		userID = 0;
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
			json.put(USER_ID, userID); //TODO: access via FaceBook user
			json.put(BEACH_ID, beachId);
			//json.put(CON, value)
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
	
	//idUser, idBeach, content, feeling, flag, sun, wind, clouds, rain

}
