package pt.up.fe.coastweather;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BeachMapFragment extends Fragment {
	private static View view;
	private static GoogleMap googleMap;
	private static final String TAG = "Map fragment";
	private static final String URL = "http://192.168.1.80/beaches.txt";
	private static float CAMERA_ZOOM = 12;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		view = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false); //TODO: ERRO ACHO QUE É ISTO
		setupMap();

		return view;
	}

	public void setupMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.location_map)).getMap();

			if (googleMap != null)
				new getBeachesTask().execute(URL);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (googleMap == null)
			googleMap = ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.location_map)).getMap();
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		if( null != googleMap ) {
			MainActivity.fragmentManager.beginTransaction()
				.remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
			
			googleMap = null;
		}
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