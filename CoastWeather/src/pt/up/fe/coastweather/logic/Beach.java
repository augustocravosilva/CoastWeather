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
	
	Beach(JSONObject json) throws JSONException {
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
	}
	
	

}
