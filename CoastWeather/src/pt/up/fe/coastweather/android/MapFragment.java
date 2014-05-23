package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
import pt.up.fe.coastweather.logic.BeachData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

	private MapView mapView;
	private static GoogleMap googleMap;
	@SuppressWarnings("unused")
	private static final String TAG = "MapFragment";
	private static float CAMERA_ZOOM = 12;
	private static boolean initialized = false;
	private static double latitude = 0, longitude = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_map, container, false);

		MapsInitializer.initialize(getActivity());
		switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
		{
		case ConnectionResult.SUCCESS:
			//Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
			mapView = (MapView) v.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);

			if( null != mapView )
			{
				googleMap = mapView.getMap();
				googleMap.getUiSettings().setMyLocationButtonEnabled(false);
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnInfoWindowClickListener(onMarkerInfoWindowClickListener);
				if( initialized )
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(latitude, longitude), CAMERA_ZOOM));
				
				refreshBeaches();
			}

			break;

		case ConnectionResult.SERVICE_MISSING: 
			Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
			break;

		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: 
			Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
			break;

		default:
			Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
		}

		return v;
	}
	
	public static void refreshBeaches() {
		if( null != googleMap ) {
			SparseArray<Beach> beaches = BeachData.getAllBeaches();
						
			for(int i = 0, size = beaches.size(); i < size; i++) {
				Beach beach = beaches.valueAt(i);

				googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(beach.getLatitude(), beach.getLongitude()))
					.title(String.valueOf(beach.getName())));
			}
		}
	}

	private final OnInfoWindowClickListener onMarkerInfoWindowClickListener = new OnInfoWindowClickListener() {
		@Override
		public void onInfoWindowClick(Marker marker) {
			Intent intent = new Intent(getActivity(), BeachActivity.class);
			intent.putExtra(BeachActivity.BEACH_ID, BeachData.getBeachByName(marker.getTitle()).getIdBeach());
			startActivity(intent);
		}
	};

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mapView != null) //added because sometimes it appear to be null
			mapView.onDestroy();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	static public void onLocationChanged(double newLatitude, double newLongitude) {
		if(null != googleMap)
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLatitude, newLongitude), CAMERA_ZOOM));
		else {

		}

		latitude = newLatitude;
		longitude = newLongitude;
		initialized = true;
	}

	public static double getLatitude() {
		return latitude;
	}

	public static double getLongitude() {
		return longitude;
	}

}