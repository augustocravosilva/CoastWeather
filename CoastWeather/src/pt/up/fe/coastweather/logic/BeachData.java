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
}
