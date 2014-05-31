package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.User;
import pt.up.fe.coastweather.logic.UserStatus;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BeachListAdapter extends BaseAdapter {
	public static final String LOG = "CoastWeather";
	Context context;
	UserStatus[] status;


	BeachListAdapter(Context c, UserStatus[] s) {
		this.context = c;
		status = s;
	}
	@Override
	public int getCount() {
		return status.length;
	}

	@Override
	public Object getItem(int arg0) {
		return status[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return status[arg0].getStatusId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_beach_list_item, null);
		}

		UserStatus user = (UserStatus) getItem(position);
		ImageView image = (ImageView) v.findViewById(R.id.icon);

		new DownloadImageTask(image)
		.execute(User.getUserPicLink(String.valueOf(user.getUserID())));
		TextView nameView = (TextView)v.findViewById(R.id.name);
		TextView descView = (TextView)v.findViewById(R.id.description);

		ImageView image_feeling = (ImageView) v.findViewById(R.id.icon_feeling);
		ImageView image_weather1 = (ImageView) v.findViewById(R.id.icon_weather1);
		ImageView image_weather2 = (ImageView) v.findViewById(R.id.icon_weather2);
		ImageView image_flag = (ImageView) v.findViewById(R.id.icon_flag);
		TextView timeView = (TextView)v.findViewById(R.id.time);


		nameView.setText(user.getUsername());

		switch(user.getFeeling()) {
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

		timeView.setText(user.getDateFormatted());

		if(user.isSunny())
			image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		else if(user.isCloudy())
			image_weather1.setImageResource(R.drawable.ic_weather_cloudy);
		else if(user.isRainy())
			image_weather1.setImageResource(R.drawable.ic_weather_rainy);

		if(user.isWindy()) {
			image_weather2.setVisibility(View.VISIBLE);
			image_weather2.setImageResource(R.drawable.ic_weather_windy);
		}
		else
			image_weather2.setVisibility(View.GONE);

		switch(user.getFlag()) {
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

}
