package pt.up.fe.coastweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class BeachActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Praia da Rocha");

		setContentView(R.layout.activity_beach);

		new DownloadImageTask((ImageView) findViewById(R.id.icon_beach_beach))
		.execute("http://c4.quickcachr.fotos.sapo.pt/i/of10410e3/6755316_Zgf0o.jpeg");

		ImageView image_feeling = (ImageView) findViewById(R.id.icon_beach_beach);
		ImageView image_weather1 = (ImageView) findViewById(R.id.icon_beach_weather1);
		ImageView image_weather2 = (ImageView) findViewById(R.id.icon_beach_weather2);
		ImageView image_flag = (ImageView) findViewById(R.id.icon_beach_flag);
		TextView timeView = (TextView) findViewById(R.id.beach_time);

		timeView.setText("5 minutes ago");      
		image_feeling.setImageResource(R.drawable.ic_feeling_2);
		image_weather1.setImageResource(R.drawable.ic_weather_sunny);
		image_weather2.setImageResource(R.drawable.ic_weather_windy);
		image_flag.setImageResource(R.drawable.ic_flag_green);


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
