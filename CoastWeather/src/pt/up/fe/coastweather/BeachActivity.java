package pt.up.fe.coastweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

public class BeachActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Praia da Rocha");
		
		setContentView(R.layout.activity_beach);
		
		new DownloadImageTask((ImageView) findViewById(R.id.icon_beach))
		.execute("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-ash2/t1.0-9/523837_507942599227785_89321393_n.jpg");
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
