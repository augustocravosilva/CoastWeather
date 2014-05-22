package pt.up.fe.coastweather.android;

import java.util.ArrayList;

import org.json.JSONException;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
import pt.up.fe.coastweather.logic.BeachData;
import pt.up.fe.coastweather.logic.Client;
import pt.up.fe.coastweather.logic.UserStatus;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BeachActivity extends Activity {
	public static final String BEACH_ID = "beach_id"; 
	private ListView list;
	private int beachId = 2;
	private Beach beach;
	public static final String LOG = "CoastWeather";
	private TextView textLatitude;
	private TextView textLongitude;
	private ImageView imageUmbrella;
	private ImageView imageRestaurant;
	private ImageView imageBlueFlag;
	private ImageView imageParking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setTitle("Praia da Rocha");

		setContentView(R.layout.activity_beach);

		textLatitude = (TextView) findViewById(R.id.beach_gps_latitude);
		textLongitude = (TextView) findViewById(R.id.beach_gps_longitude);
		imageUmbrella = (ImageView) findViewById(R.id.icon_beach_umbrella);
		imageRestaurant = (ImageView) findViewById(R.id.icon_beach_restaurant);
		imageBlueFlag = (ImageView) findViewById(R.id.icon_beach_blue_flag);
		imageParking = (ImageView) findViewById(R.id.icon_beach_parking);

		beachId = getIntent().getIntExtra(BEACH_ID, 2);
		
		beach = BeachData.getBeach(beachId);
		
		new DownloadImageTask((ImageView) findViewById(R.id.icon_beach_beach))
		.execute(beach.getPicture());
		
		if(!beach.isParking())
			imageParking.setVisibility(View.GONE);

		if(!beach.isUmbrella())
			imageUmbrella.setVisibility(View.GONE);

		if(!beach.isRestaurant())
			imageRestaurant.setVisibility(View.GONE);

		if(!beach.isBlueFlag())
			imageBlueFlag.setVisibility(View.GONE);

		getActionBar().setTitle(beach.getName());
		
		//textLatitude.setText(Html.fromHtml("<b>latitude: </b>" + Double.toString(beach.getLatitude())));
		//textLongitude.setText(Html.fromHtml("<b>longitude: </b>" + Double.toString(beach.getLongitude())));

		

		ImageView image_feeling = (ImageView) findViewById(R.id.icon_beach_feeling);
		ImageView image_weather1 = (ImageView) findViewById(R.id.icon_beach_weather1);
		ImageView image_weather2 = (ImageView) findViewById(R.id.icon_beach_weather2);
		ImageView image_flag = (ImageView) findViewById(R.id.icon_beach_flag);
		TextView timeView = (TextView) findViewById(R.id.beach_time);

		timeView.setText("5 minutes ago");  
		image_feeling.setImageResource(R.drawable.ic_feeling_2);
		image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		image_weather2.setImageResource(R.drawable.ic_weather_windy);
		image_flag.setImageResource(R.drawable.ic_flag_green);

		/*TextView textLatitude = (TextView) findViewById(R.id.beach_gps_latitude);
		TextView textLongitude = (TextView) findViewById(R.id.beach_gps_longitude);*/

		textLatitude.setText(Html.fromHtml("<b>latitude: </b>" + beach.getLatitude()));
		textLongitude.setText(Html.fromHtml("<b>longitude: </b>" + beach.getLongitude()));

		list = (ListView) findViewById(R.id.beach_status_list);
		list.setAdapter(new BeachListAdapter(this));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<UserStatus>> {

		@Override
		protected  ArrayList<UserStatus> doInBackground(String... urls) {
			/*try {
				String result = Client.GET(urls[0],Integer.toString(beachId));
				
				return null;
			} catch (JSONException e) {
				Log.w(LOG, "BeachActivity json  " + e.getMessage());
			}*/
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		//@Override
		protected void onPostExecute(ArrayList<UserStatus> result) {
		
	}
}
}
