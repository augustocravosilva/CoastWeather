package pt.up.fe.coastweather.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
import pt.up.fe.coastweather.logic.BeachData;
import pt.up.fe.coastweather.logic.Client;
import pt.up.fe.coastweather.logic.UserStatus;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BeachActivity extends Activity {
	public static final String BEACH_ID = "beach_id"; 
	private static final int NUMBER_OF_STATUS = 15;
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
	private View separator;

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
		separator = findViewById(R.id.beach_separator);

		beachId = getIntent().getIntExtra(BEACH_ID, 2);

		beach = BeachData.getBeach(beachId);

		try{
			new DownloadImageTask((ImageView) findViewById(R.id.icon_beach_beach))
			.execute(beach.getPicture());
		}
		catch(Exception e){
			Log.e(LOG, "BeachActivity - error downloading image");
		}

		if(beach != null) {
			if(!beach.isParking())
				imageParking.setVisibility(View.GONE);

			if(!beach.isUmbrella())
				imageUmbrella.setVisibility(View.GONE);

			if(!beach.isRestaurant())
				imageRestaurant.setVisibility(View.GONE);

			if(!beach.isBlueFlag())
				imageBlueFlag.setVisibility(View.GONE);

			getActionBar().setTitle(beach.getName());
			getActionBar().setSubtitle(beach.getPlace());

			//textLatitude.setText(Html.fromHtml("<b>latitude: </b>" + Double.toString(beach.getLatitude())));
			//textLongitude.setText(Html.fromHtml("<b>longitude: </b>" + Double.toString(beach.getLongitude())));



			ImageView image_feeling = (ImageView) findViewById(R.id.icon_beach_feeling);
			ImageView image_weather1 = (ImageView) findViewById(R.id.icon_beach_weather1);
			ImageView image_weather2 = (ImageView) findViewById(R.id.icon_beach_weather2);
			ImageView image_flag = (ImageView) findViewById(R.id.icon_beach_flag);
			TextView timeView = (TextView) findViewById(R.id.beach_time);

			timeView.setText("");  

			switch(beach.getFeeling()) {
			case 0:
				image_feeling.setImageResource(R.drawable.ic_feeling_m2);
				break;
			case 1:
				image_feeling.setImageResource(R.drawable.ic_feeling_m1);
				break;
			case 2:
				image_feeling.setImageResource(R.drawable.ic_feeling_0);
				break;
			case 3:
				image_feeling.setImageResource(R.drawable.ic_feeling_1);
				break;
			case 4:
				image_feeling.setImageResource(R.drawable.ic_feeling_2);
				break;
			default:
				image_feeling.setImageResource(R.drawable.ic_feeling);
				break;
			}

			//image_feeling.setImageResource(R.drawable.ic_feeling_2);

			if(beach.isSunny())
				image_weather1.setImageResource(R.drawable.ic_weather_sunny);
			else if(beach.isCloudy())
				image_weather1.setImageResource(R.drawable.ic_weather_cloudy);
			else if(beach.isRainy())
				image_weather1.setImageResource(R.drawable.ic_weather_rainy);
			else
				image_weather1.setImageResource(R.drawable.ic_weather_sunny_grey);

			if(beach.isWindy()) {
				image_weather2.setVisibility(View.VISIBLE);
				image_weather2.setImageResource(R.drawable.ic_weather_windy);
			}
			else
				image_weather2.setVisibility(View.GONE);

			//image_weather1.setImageResource(R.drawable.ic_weather_sunny);
			//image_weather2.setImageResource(R.drawable.ic_weather_windy);

			switch(beach.getFlag()) {
			case 0:
				image_flag.setImageResource(R.drawable.ic_flag_green);
				break;
			case 1:
				image_flag.setImageResource(R.drawable.ic_flag_yellow);
				break;
			case 2:
				image_flag.setImageResource(R.drawable.ic_flag_red);
				break;
			case 3:
				image_flag.setImageResource(R.drawable.ic_flag_black);
				break;
			default:
				image_flag.setImageResource(R.drawable.ic_flag_grey);
			}

			/*TextView textLatitude = (TextView) findViewById(R.id.beach_gps_latitude);
		TextView textLongitude = (TextView) findViewById(R.id.beach_gps_longitude);*/

			textLatitude.setText(Html.fromHtml("<b>latitude: </b>" + beach.getLatitude()));
			textLongitude.setText(Html.fromHtml("<b>longitude: </b>" + beach.getLongitude()));

			/*list = (ListView) findViewById(R.id.beach_status_list);
		list.setAdapter(new BeachListAdapter(this));*/

			new HttpAsyncTask(this).execute(Client.GET_STATUS_BY_BEACH_ID);
		}
		else 
			finish();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, UserStatus[]> {
		Context context;

		HttpAsyncTask(Context c) {
			context = c;
		}

		@Override
		protected UserStatus[] doInBackground(String... urls) {
			try {
				String result = Client.GET(urls[0],Integer.toString(beachId));
				JSONArray array = new JSONObject(result).getJSONArray("status");

				if(array.length() == 0)
					return null;
				UserStatus[] status = new UserStatus[NUMBER_OF_STATUS < array.length() ? NUMBER_OF_STATUS : array.length()];

				for(int i = 0; i < status.length; i++)
					status[i] = new UserStatus(array.getJSONObject(i));

				return status;
			} catch (JSONException e) {
				Log.w(LOG, "BeachActivity json  " + e.getMessage());
			}

			return null;
		}
		@Override
		protected void onPostExecute(UserStatus[] result) {
			if(result != null) {
				separator.setVisibility(View.VISIBLE);

				list = (ListView) findViewById(R.id.beach_status_list);
				list.setAdapter(new BeachListAdapter(context,result));
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(BEACH_ID, beachId);
	}
}
