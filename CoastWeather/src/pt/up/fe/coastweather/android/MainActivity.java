package pt.up.fe.coastweather.android;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
import pt.up.fe.coastweather.logic.BeachData;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private static final String TAG = "MainActivity";
	private static final float LOCATION_REFRESH_DISTANCE = 50;
	private static final long LOCATION_REFRESH_TIME = 5000;
	private static final String URL = "http://paginas.fe.up.pt/~ei11068/coastWeather/v1/index.php/beaches";
	private MenuItem menuItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //TODO: delete this
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}



		LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
				LOCATION_REFRESH_DISTANCE, mLocationListener);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME,
				LOCATION_REFRESH_DISTANCE, mLocationListener);
		mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, LOCATION_REFRESH_TIME,
				LOCATION_REFRESH_DISTANCE, mLocationListener);

		Location location = mLocationManager.getLastKnownLocation(mLocationManager.getBestProvider(new Criteria(), false));
		if( null != location )
			MapFragment.onLocationChanged(location.getLatitude(), location.getLongitude());

		new getBeachesTask().execute(URL);
	}

	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {

			if( null != location ) {
				Log.i("onLocationChanged", "Mudou");
				MapFragment.onLocationChanged(location.getLatitude(), location.getLongitude());
			} else Log.i("onLocationChanged", "Mudou e null");

		}

		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {}

		@Override
		public void onProviderEnabled(String s) {}

		@Override
		public void onProviderDisabled(String s) {}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	protected void onPause() {

		super.onPause();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			menuItem = item;
			menuItem.setActionView(R.layout.menu_progressbar);
			menuItem.expandActionView();
			new getBeachesTask().execute(URL);
			
			break;
		default:
			break;
		}
		return true;
	}
	
	private class getBeachesTask extends AsyncTask<String, Void, List<Beach>> {
		private final AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<Beach> doInBackground(String... params) {
			try {
				return mClient.execute(new HttpGet(params[0]),
						new BeachesJSONResponseHandler());
			} catch (ClientProtocolException e) {
				Log.e(TAG, "ClientProtocolException", e);
			} catch (IOException e) {
				Log.e(TAG, "IOException", e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Beach> result) {
			if( null != result ) {
				BeachData.clearBeaches();
				
				for( Beach beach : result ) {
					BeachData.addBeach(beach);
				}
				
				MapFragment.refreshBeaches();
			}
					
			
			if ( null != mClient )
				mClient.close();
			
			if( null != menuItem ) {
				menuItem.collapseActionView();
				menuItem.setActionView(null);
			}
		}
	}
}
