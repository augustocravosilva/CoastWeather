package pt.up.fe.coastweather.android;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import pt.up.fe.coastweather.R;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

	private MapView mapView;
	private static GoogleMap googleMap;
	private static final String TAG = "MapFragment";
	private static final String URL = "http://192.168.1.80/beaches.txt";
	private static float CAMERA_ZOOM = 12;

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
				new getBeachesTask().execute(URL);
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
	
	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	static public void onLocationChanged(double latitude, double longitude) {
		if(null != googleMap)
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), CAMERA_ZOOM));
	}

	private class getBeachesTask extends AsyncTask<String, Void, List<Beach>> {
		private static final String TAG = "getBeachesTask";
		private AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<Beach> doInBackground(String... params) {
			try {
				return mClient.execute(new HttpGet(params[0]),
						new JSONResponseHandler());
			} catch (ClientProtocolException e) {
				Log.i(TAG, "ClientProtocolException");
			} catch (IOException e) {
				Log.i(TAG, "IOException");
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Beach> result) {

			if( null != googleMap && null != result ) {
				for( Beach beach : result ) {
					googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(beach.getLatitude(), beach.getLongitude()))
					.title(String.valueOf(beach.getName())));
				}			
			}

			if (null != mClient)
				mClient.close();
		}
	}
}