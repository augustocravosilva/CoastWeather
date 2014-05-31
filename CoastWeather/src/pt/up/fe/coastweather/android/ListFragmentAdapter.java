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
		ViewHolder holder;
		if (convertView == null) {
			convertView = ((LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.fragment_list_item, null);
			holder = new ViewHolder();
			holder.beachView = (TextView)convertView.findViewById(R.id.beach);
			holder.descView = (TextView)convertView.findViewById(R.id.description);
			holder.image_feeling = (ImageView)convertView.findViewById(R.id.icon_feeling);
			holder.image_weather1 = (ImageView)convertView.findViewById(R.id.icon_weather1);
			holder.image_weather2 = (ImageView)convertView.findViewById(R.id.icon_weather2);
			holder.image_flag = (ImageView)convertView.findViewById(R.id.icon_flag);
			holder.timeView = (TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Beach beach = (Beach) getItem(position);

		holder.beachView.setText(Html.fromHtml("<b>" + beach.getName() + "</b> - " + beach.getPlace()));

		holder.timeView.setText("");      
		holder.timeView.setVisibility(View.GONE);      

		switch(beach.getFeeling()) {
		case 0:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_m2_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_m2);
			break;
		case 1:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_m1_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_m1);
			break;
		case 2:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_0_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_0);
			break;
		case 3:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_1_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_1);
			break;
		case 4:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_2_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_2);
			break;
		default:
			holder.descView.setText("- " + context.getResources().getString(R.string.feeling_0_text2));
			holder.image_feeling.setImageResource(R.drawable.ic_feeling_0);
			break;
		}

		if(beach.isSunny())
			holder.image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		else if(beach.isCloudy())
			holder.image_weather1.setImageResource(R.drawable.ic_weather_cloudy);
		else if(beach.isRainy())
			holder.image_weather1.setImageResource(R.drawable.ic_weather_rainy);

		if(beach.isWindy()) {
			holder.image_weather2.setVisibility(View.VISIBLE);
			holder.image_weather2.setImageResource(R.drawable.ic_weather_windy);
		}
		else
			holder.image_weather2.setVisibility(View.GONE);


		switch(beach.getFlag()) {
		case 0:
			holder.image_flag.setVisibility(View.VISIBLE);
			holder.image_flag.setImageResource(R.drawable.ic_flag_green);
			break;
		case 1:
			holder.image_flag.setVisibility(View.VISIBLE);
			holder.image_flag.setImageResource(R.drawable.ic_flag_yellow);
			break;
		case 2:
			holder.image_flag.setVisibility(View.VISIBLE);
			holder.image_flag.setImageResource(R.drawable.ic_flag_red);
			break;
		case 3:
			holder.image_flag.setVisibility(View.VISIBLE);
			holder.image_flag.setImageResource(R.drawable.ic_flag_black);
			break;
		default:
			holder.image_flag.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView beachView;
		TextView descView;
		ImageView image_feeling;
		ImageView image_weather1;
		ImageView image_weather2;
		ImageView image_flag;
		TextView timeView;
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
