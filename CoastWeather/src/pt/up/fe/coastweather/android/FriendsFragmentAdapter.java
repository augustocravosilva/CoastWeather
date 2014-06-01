package pt.up.fe.coastweather.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Client;
import pt.up.fe.coastweather.logic.User;
import pt.up.fe.coastweather.logic.UserStatus;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsFragmentAdapter extends BaseAdapter {
	Context context;
	private static final String LOG = "FriendList";
	private ArrayList<UserStatus> statuses;

	FriendsFragmentAdapter(Context c) {
		this.context = c;
	}
	@Override
	public int getCount() {
		if(statuses != null)
			return statuses.size();
		return 0;
	}

	synchronized public void updateData()
	{

		if(User.getInstance().isLoggedIn())
			new HttpAsyncTask().execute(Client.GET_FRIENDS_ACT);
	}

	@Override
	public UserStatus getItem(int arg0) {
		if(statuses!=null)
			return statuses.get(arg0);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return statuses.get(arg0).getStatusId();
	}


	public String getStatusString(int id)
	{
		String out = "";
		switch (id) {
		case 0:
			out = context.getResources().getString(R.string.feeling_m2_text);
			break;
		case 1:
			out = context.getResources().getString(R.string.feeling_m1_text);
			break;
		case 2:
			out = context.getResources().getString(R.string.feeling_0_text);
			break;
		case 3:
			out = context.getResources().getString(R.string.feeling_1_text);
			break;
		case 4:
			out = context.getResources().getString(R.string.feeling_2_text);
			break;
		default:
			break;
		}
		return out;
	}

	public int getStatusPic(int id)
	{
		int out = 0;
		switch (id) {
		case 0:
			out = R.drawable.ic_feeling_m2;
			break;
		case 1:
			out = R.drawable.ic_feeling_m1;
			break;
		case 2:
			out = R.drawable.ic_feeling_0;
			break;
		case 3:
			out = R.drawable.ic_feeling_1;
			break;
		case 4:
			out = R.drawable.ic_feeling_2;
			break;
		default:
			break;
		}
		return out;
	}

	public int getFlagPic(int id)
	{
		int out = 0;
		switch (id) {
		case UserStatus.FLAG_BLACK:
			out = R.drawable.ic_flag_black;
			break;
		case UserStatus.FLAG_GREEN:
			out = R.drawable.ic_flag_green;
			break;
		case UserStatus.FLAG_RED:
			out = R.drawable.ic_flag_red;
			break;
		case UserStatus.FLAG_YELLOW:
			out = R.drawable.ic_flag_yellow;
			break;
		default:
			break;
		}
		return out;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(LOG, "Draw view");
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.fragment_friends_item, null);
		}

		ImageView image = (ImageView) v.findViewById(R.id.icon);
		TextView nameView = (TextView)v.findViewById(R.id.name);
		TextView beachView = (TextView)v.findViewById(R.id.beach);
		TextView descView = (TextView)v.findViewById(R.id.description);

		ImageView image_feeling = (ImageView) v.findViewById(R.id.icon_feeling);
		ImageView image_weather1 = (ImageView) v.findViewById(R.id.icon_weather1);
		ImageView image_weather2 = (ImageView) v.findViewById(R.id.icon_weather2);
		ImageView image_flag = (ImageView) v.findViewById(R.id.icon_flag);
		TextView timeView = (TextView)v.findViewById(R.id.time);


		if(User.getInstance().isLoggedIn() && statuses!=null)
		{
			UserStatus us = statuses.get(position);
			nameView.setText(us.getUsername());
			beachView.setText(us.getBeachName() + " - " + us.getPlace());
			Log.d(LOG,"->"+us.getFeeling());
			descView.setText(getStatusString(us.getFeeling()));
			timeView.setText(us.getDateFormatted());   
			image_feeling.setImageResource(getStatusPic(us.getFeeling()));
			if(us.isCloudy())
				image_weather1.setImageResource(R.drawable.ic_weather_cloudy);
			else if(us.isRainy())
				image_weather1.setImageResource(R.drawable.ic_weather_rainy);
			else if(us.isSunny())
				image_weather1.setImageResource(R.drawable.ic_weather_sunny);
			else
				image_weather1.setVisibility(View.GONE);
			if(us.isWindy())
				image_weather2.setImageResource(R.drawable.ic_weather_windy);
			else image_weather2.setVisibility(View.GONE);
			int idFlag = getFlagPic(us.getFlag());
			if(idFlag>0)
				image_flag.setImageResource(idFlag);
			else image_flag.setVisibility(View.GONE);
			image.setImageResource(R.drawable.ic_launcher);
			new DownloadImageTask(image).execute(User.getUserPicLink(String.valueOf(us.getUserID())));
		}

		return v;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<UserStatus>> {

		@Override
		protected ArrayList<UserStatus> doInBackground(String... urls) {
			try {
				ArrayList<UserStatus> statusArray = new ArrayList<UserStatus>();
				String list = User.getInstance().friendsArrayAsString();
				Log.d(LOG,list);
				String out = Client.GET(urls[0],list);
				Log.d(LOG,"--"+out);
				JSONArray arr = new JSONArray((new JSONObject(out)).get("status").toString());
				for(int i = 0; i < arr.length(); i++)
				{
					UserStatus us = new UserStatus(arr.getString(i));
					statusArray.add(us);
				}
				return statusArray;
			} catch (Exception e) {
				Log.w(LOG, "json  " + e.getMessage());
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		//@Override
		synchronized protected void onPostExecute(ArrayList<UserStatus> result) {
			if (result != null) {
				statuses=result;
				notifyDataSetChanged();
			}
		}
	}



}
