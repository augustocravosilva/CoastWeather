package pt.up.fe.coastweather.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import pt.up.fe.coastweather.logic.Beach;
import android.util.Log;

public class BeachesJSONResponseHandler implements	ResponseHandler<List<Beach>> {
	private static String TAG = "MainActivity";
	@Override
	public List<Beach> handleResponse(HttpResponse response) throws IOException {

		List<Beach> result = new ArrayList<Beach>();
		String JSONResponse = new BasicResponseHandler().handleResponse(response);

		try {
			JSONObject object = (JSONObject) new JSONTokener(JSONResponse).nextValue();
			JSONArray beaches = object.getJSONArray("beaches");

			for (int i = 0; i < beaches.length(); i++) {
				JSONObject tmp = (JSONObject) beaches.get(i);
				result.add(new Beach(tmp));
			}
		} catch (JSONException e) {
			Log.e(TAG, "JSON Exception", e);
		}
		return result;
	}
}
