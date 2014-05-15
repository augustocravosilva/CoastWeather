package pt.up.fe.coastweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class DummySectionFragment extends Fragment {
	
	public static final String LOG_TEST_MESSAGE = "TESTING_JSON";
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView etResponse;
	private TextView tvIsConnected;

	public DummySectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_dummy,
				container, false);
		etResponse = (TextView) rootView
				.findViewById(R.id.section_label);
		etResponse.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));

		tvIsConnected = (TextView) rootView.findViewById(R.id.tvIsConnected);

		// check if you are connected or not
		if(isConnected()){
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You are conncted");
		}
		else{
			tvIsConnected.setText("You are NOT conncted");
		}

		// call AsynTask to perform network operation on separate thread
		new HttpAsyncTask().execute("http://paginas.fe.up.pt/~ei11068/coastWeather/v1/index.php/status/1");


		Button b = (Button) rootView.findViewById(R.id.button_action);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				etResponse.setText("benfica");

			}
		});
		return rootView;
	}

	public static String GET(String url){
		InputStream inputStream = null;
		String result = "";
		UserStatus user = null;
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if(inputStream != null) {
				result = convertInputStreamToString(inputStream);
				user = new UserStatus(result);
				Log.i(LOG_TEST_MESSAGE, user.toString());
			}
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
			return true;
		else
			return false;   
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getActivity().getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
			etResponse.setText(result);
		}
	}
}