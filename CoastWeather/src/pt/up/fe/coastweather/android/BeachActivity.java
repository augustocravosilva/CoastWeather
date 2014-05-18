package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BeachActivity extends Activity {

	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Praia da Rocha");

		setContentView(R.layout.activity_beach);

		new DownloadImageTask((ImageView) findViewById(R.id.icon_beach_beach))
		.execute("http://c4.quickcachr.fotos.sapo.pt/i/of10410e3/6755316_Zgf0o.jpeg");

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
		
	/*	final float scale = getResources().getDisplayMetrics().density;
		int pixels = (int) (50 * scale + 0.5f);
		
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.beach_extra_info);
		ImageView img = new ImageView(this);
		img.setImageResource(R.drawable.ic_beach_coordinates);
		img.setLayoutParams(new LayoutParams(pixels, pixels));
		linearLayout.addView(img);*/
		TextView textLatitude = (TextView) findViewById(R.id.beach_gps_latitude);
		TextView textLongitude = (TextView) findViewById(R.id.beach_gps_longitude);
		
		textLatitude.setText(Html.fromHtml("<b>latitude: </b>" + 37.11801));
		textLongitude.setText(Html.fromHtml("<b>longitude: </b>" + -8.536353));
		
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
}
