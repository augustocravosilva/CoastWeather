package pt.up.fe.coastweather.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Beach;
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

	//TODO how to trigger update after loggin
	public void updateData()
	{

		if(User.getInstance().isLoggedIn() && statuses==null)
			new HttpAsyncTask().execute(Client.GET_FRIENDS_ACT);
	}
	
	@Override
	public Object getItem(int arg0) {
		if(statuses!=null)
			return statuses.get(arg0);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.fragment_list_item, null);
		}

		ImageView image = (ImageView) v.findViewById(R.id.icon);
		//new DownloadImageTask(image).execute("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-ash2/t1.0-9/523837_507942599227785_89321393_n.jpg");
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
				beachView.setText(us.getBeachName());
				descView.setText(us.getFeeling()); //TODO converter
				timeView.setText(us.getDate()); //TODO other format?    
				image_feeling.setImageResource(R.drawable.ic_feeling_2); //TODO
				image_weather1.setImageResource(R.drawable.ic_weather_sunny);
				image_weather2.setImageResource(R.drawable.ic_weather_windy);
				image_flag.setImageResource(R.drawable.ic_flag_green);
		}
		
		return v;
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<UserStatus>> {

		@Override
		protected ArrayList<UserStatus> doInBackground(String... urls) {
			try {
				ArrayList<UserStatus> statusArray = new ArrayList<UserStatus>();
				String out = Client.GET(urls[0],User.getInstance().friendsArrayAsString());
				JSONArray arr = new JSONArray(out);
				for(int i = 0; i < arr.length(); i++)
				{
					UserStatus us = new UserStatus(arr.getString(i));
					statusArray.add(us);
				}
				return statusArray;
			} catch (JSONException e) {
				Log.w(LOG, "json  " + e.getMessage());
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		//@Override
		protected void onPostExecute(ArrayList<UserStatus> result) {
			if (result != null) {
				statuses=result;
				notifyDataSetChanged();
		}
	}
}

	

}
