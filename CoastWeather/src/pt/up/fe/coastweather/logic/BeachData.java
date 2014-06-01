package pt.up.fe.coastweather.logic;

import android.util.SparseArray;

public class BeachData {
	private static SparseArray<Beach> beaches = new SparseArray<Beach>();
	
	public static Beach getBeach(Integer idBeach) {
		return beaches.get(idBeach);
	}
	
	public static void addBeach(Beach beach) {
		beaches.put(beach.getIdBeach(), beach);
	}
	
	public static SparseArray<Beach> getAllBeaches() {
		return beaches;
	}
	
	public static void clearBeaches() {
		beaches.clear();
	}
	
	public static Beach getBeachByNameAndPlace(String name, String place) {
		for(int i = 0, size = beaches.size(); i < size; i++)
			if( beaches.valueAt(i).getName().equals(name) && beaches.valueAt(i).getPlace().equals(place) )
				return beaches.valueAt(i);
		
		return null;
	}
}
