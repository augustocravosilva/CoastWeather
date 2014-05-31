package pt.up.fe.coastweather.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class Beach {

	private static final String ID_BEACH = "idBeach";
	private static final String NAME = "name";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String PLACE = "place";
	private static final String PICTURE = "picture";
	private static final String PARKING = "parking";
	private static final String BLUE_FLAG = "blueFlag";
	private static final String RESTAURANT = "restaurant";
	private static final String UMBRELLA = "umbrella";

	private int idBeach;
	private String name;
	private double latitude;
	private double longitude;
	private String place;
	private String picture;

	private boolean parking;
	private boolean blueFlag;
	private boolean restaurant;
	private boolean umbrella;


	public final static int FLAG_GREEN = 0;
	public final static int FLAG_YELLOW = 1;
	public final static int FLAG_RED = 2;
	public final static int FLAG_BLACK = 3;
	private final String FEELING = "feeling";
	private final String SUNNY = "sun";
	private final String WINDY = "wind";
	private final String CLOUDY = "clouds";
	private final String RAINY = "rain";
	private final String FLAG = "flag";
	private final String NUM_STATUS = "numStatus";

	private int numStatus;
	private int feeling;

	private boolean sunny;
	private boolean windy;
	private boolean cloudy;
	private boolean rainy;

	private int flag;

	public Beach(JSONObject json) throws JSONException {
		processJson(json);
	}

	public Beach(String beach) throws JSONException {
		JSONObject json = new JSONObject(beach);
		processJson(json);
	}


	public Beach(String newName, double newLatitude, double newLongitude) {
		latitude = newLatitude;
		longitude = newLongitude;
		name = newName;
	}

	private void processJson(JSONObject json) throws JSONException {
		idBeach = json.getInt(ID_BEACH);
		name = json.getString(NAME);
		latitude = json.getDouble(LATITUDE);
		longitude = json.getDouble(LONGITUDE);
		place = json.getString(PLACE);
		picture = json.getString(PICTURE);

		parking =  Boolean.parseBoolean(json.getString(PARKING));
		blueFlag =  Boolean.parseBoolean(json.getString(BLUE_FLAG));
		restaurant =  Boolean.parseBoolean(json.getString(RESTAURANT));
		umbrella =  Boolean.parseBoolean(json.getString(UMBRELLA));

		if(json.has(NUM_STATUS)) {
			numStatus = json.getInt(NUM_STATUS);
			if(numStatus == 0) {
				feeling = -1;
				sunny = false;
				windy = false;
				cloudy = false;
				rainy = false;
				flag = -1;
			}
			else {
				feeling = json.getInt(FEELING);
				sunny = Boolean.parseBoolean(json.getString(SUNNY));
				windy = Boolean.parseBoolean(json.getString(WINDY));
				cloudy = Boolean.parseBoolean(json.getString(CLOUDY));
				rainy = Boolean.parseBoolean(json.getString(RAINY));
				flag = json.getInt(FLAG);
			}
		}
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public int getIdBeach() {
		return idBeach;
	}

	public void setIdBeach(int idBeach) {
		this.idBeach = idBeach;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isBlueFlag() {
		return blueFlag;
	}

	public void setBlueFlag(boolean blueFlag) {
		this.blueFlag = blueFlag;
	}

	public boolean isRestaurant() {
		return restaurant;
	}

	public void setRestaurant(boolean restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isUmbrella() {
		return umbrella;
	}

	public void setUmbrella(boolean umbrella) {
		this.umbrella = umbrella;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getFeeling() {
		return feeling;
	}

	public boolean isSunny() {
		return sunny;
	}

	public boolean isWindy() {
		return windy;
	}

	public boolean isCloudy() {
		return cloudy;
	}

	public boolean isRainy() {
		return rainy;
	}

	public int getFlag() {
		return flag;
	}

}
