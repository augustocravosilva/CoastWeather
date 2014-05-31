package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListFragmentAdapter extends BaseAdapter {
	Context context;


	ListFragmentAdapter(Context c) {
		this.context = c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
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

		/*ImageView image = (ImageView) v.findViewById(R.id.icon);
		new DownloadImageTask(image)
		.execute("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-ash2/t1.0-9/523837_507942599227785_89321393_n.jpg");
		TextView nameView = (TextView)v.findViewById(R.id.name);*/
		TextView beachView = (TextView)v.findViewById(R.id.beach);
		TextView descView = (TextView)v.findViewById(R.id.description);

		ImageView image_feeling = (ImageView) v.findViewById(R.id.icon_feeling);
		ImageView image_weather1 = (ImageView) v.findViewById(R.id.icon_weather1);
		ImageView image_weather2 = (ImageView) v.findViewById(R.id.icon_weather2);
		ImageView image_flag = (ImageView) v.findViewById(R.id.icon_flag);
		TextView timeView = (TextView)v.findViewById(R.id.time);
		
		//image.setImageResource(R.drawable.ic_feeling_0);
		//nameView.setText("Tiago Fernandes");
		beachView.setText(Html.fromHtml("<b>Praia da Rocha</b> - Portimão"));
		descView.setText("Feeling awesome");
		timeView.setText("5 minutes ago");      
		image_feeling.setImageResource(R.drawable.ic_feeling_2);
		image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		image_weather2.setImageResource(R.drawable.ic_weather_windy);
		image_flag.setImageResource(R.drawable.ic_flag_green);

		return v;
	}

}
