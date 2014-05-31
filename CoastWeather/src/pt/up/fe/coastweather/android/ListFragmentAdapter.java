package pt.up.fe.coastweather.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
import pt.up.fe.coastweather.logic.Client;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListFragmentAdapter extends BaseAdapter {
	private static final String LOG = "CoastWeather";

	Context context;
	Beach[] beaches = null;
	int numbBeaches;


	ListFragmentAdapter(Context c, int num) {
		this.context = c;
		this.numbBeaches = num;
	}

	synchronized public void updateData()
	{
		new HttpAsyncTask().execute(Client.GET_BEACHES_BY_LOCATION);
	}

	@Override
	public int getCount() {
		if (beaches != null)
			return beaches.length;

		return 0;
	}

	@Override
	public Beach getItem(int arg0) {
		return beaches[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return beaches[arg0].getIdBeach();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.fragment_list_item, null);
		}
		Beach beach = (Beach) getItem(position);

		TextView beachView = (TextView)v.findViewById(R.id.beach);
		TextView descView = (TextView)v.findViewById(R.id.description);
		ImageView image_feeling = (ImageView) v.findViewById(R.id.icon_feeling);
		ImageView image_weather1 = (ImageView) v.findViewById(R.id.icon_weather1);
		ImageView image_weather2 = (ImageView) v.findViewById(R.id.icon_weather2);
		ImageView image_flag = (ImageView) v.findViewById(R.id.icon_flag);
		TextView timeView = (TextView)v.findViewById(R.id.time);

		beachView.setText(Html.fromHtml("<b>" + beach.getName() + "</b> - " + beach.getPlace()));

		timeView.setText(beach.getLatitude()+"");      

		switch(beach.getFeeling()) {
		case 0:
			descView.setText("- " + context.getResources().getString(R.string.feeling_m2_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_m2);
			break;
		case 1:
			descView.setText("- " + context.getResources().getString(R.string.feeling_m1_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_m1);
			break;
		case 2:
			descView.setText("- " + context.getResources().getString(R.string.feeling_0_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_0);
			break;
		case 3:
			descView.setText("- " + context.getResources().getString(R.string.feeling_1_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_1);
			break;
		case 4:
			descView.setText("- " + context.getResources().getString(R.string.feeling_2_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_2);
			break;
		default:
			descView.setText("- " + context.getResources().getString(R.string.feeling_0_text2));
			image_feeling.setImageResource(R.drawable.ic_feeling_0);
			break;
		}

		if(beach.isSunny())
			image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		else if(beach.isCloudy())
			image_weather1.setImageResource(R.drawable.ic_weather_cloudy);
		else if(beach.isRainy())
			image_weather1.setImageResource(R.drawable.ic_weather_rainy);

		if(beach.isWindy()) {
			image_weather2.setVisibility(View.VISIBLE);
			image_weather2.setImageResource(R.drawable.ic_weather_windy);
		}
		else
			image_weather2.setVisibility(View.GONE);


		switch(beach.getFlag()) {
		case 0:
			image_flag.setVisibility(View.VISIBLE);
			image_flag.setImageResource(R.drawable.ic_flag_green);
			break;
		case 1:
			image_flag.setVisibility(View.VISIBLE);
			image_flag.setImageResource(R.drawable.ic_flag_yellow);
			break;
		case 2:
			image_flag.setVisibility(View.VISIBLE);
			image_flag.setImageResource(R.drawable.ic_flag_red);
			break;
		case 3:
			image_flag.setVisibility(View.VISIBLE);
			image_flag.setImageResource(R.drawable.ic_flag_black);
			break;
		default:
			image_flag.setVisibility(View.GONE);
		}

		return v;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Beach[]> {
		HttpAsyncTask() {

		}
		@Override
		protected Beach[] doInBackground(String... urls) {

			double latitude, longitude;

			if(MainActivity.isHasLastKnownLocation()) {
				latitude = MainActivity.getLastKnownLatitude();
				longitude = MainActivity.getLastKnownLongitude();
			}
			else {
				latitude = MapFragment.getLatitude();
				longitude = MapFragment.getLongitude();
			}

			String data = Client.GET(urls[0], latitude + "/" + longitude + "/" + numbBeaches);

			try {
				JSONObject j = new JSONObject(data);
				if (j.getBoolean("error"))
					return null;

				JSONArray array = j.getJSONArray("beaches");
				j = null;

				Beach [] beaches = new Beach[array.length()];

				for (int i = 0; i < array.length();i++) 
					beaches[i] = new Beach(array.getJSONObject(i));

				return beaches;

			} catch (JSONException e) {
				Log.w(LOG, "Json exception " + e.getMessage());
				e.printStackTrace();
			}
			catch (Exception e) {
				Log.w(LOG, "Addreview " + e.getMessage());
				e.printStackTrace();
			}
			return null;

		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		synchronized protected void onPostExecute(Beach[] result) {
			if(result != null) {
				beaches = result;
				notifyDataSetChanged();
			}
		}
	}
}
