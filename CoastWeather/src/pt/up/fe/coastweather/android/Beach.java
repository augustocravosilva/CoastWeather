package pt.up.fe.coastweather.android;

public class Beach {
    private double latitude, longitude;
    private String name;

    protected Beach(String newName, double newLatitude, double newLongitude) {
        latitude = newLatitude;
        longitude = newLongitude;
        name = newName;
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
}
