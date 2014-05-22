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
	
	public Beach(JSONObject json) throws JSONException {
		idBeach = json.getInt(ID_BEACH);
		name = json.getString(NAME);
		latitude = json.getDouble(LATITUDE);
		longitude = json.getDouble(LONGITUDE);
		
		//TODO quando estiver no servidor, descomentar
		/*
		place = json.getString(PLACE);
		picture = json.getString(PICTURE);
		
		parking =  Boolean.parseBoolean(json.getString(PARKING));
		blueFlag =  Boolean.parseBoolean(json.getString(BLUE_FLAG));
		restaurant =  Boolean.parseBoolean(json.getString(RESTAURANT));
		umbrella =  Boolean.parseBoolean(json.getString(UMBRELLA));
		*/
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

}
