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

public class JSONResponseHandler implements	ResponseHandler<List<Beach>> {
	@Override
	public List<Beach> handleResponse(HttpResponse response) throws IOException {

		List<Beach> result = new ArrayList<Beach>();
		String JSONResponse = new BasicResponseHandler().handleResponse(response);

		try {

			JSONObject object = (JSONObject) new JSONTokener(JSONResponse).nextValue();
			JSONArray beaches = object.getJSONArray("beaches");

			for (int i = 0; i < beaches.length(); i++) {

				JSONObject tmp = (JSONObject) beaches.get(i);
				result.add(new Beach(tmp.getString("name"), tmp.getDouble("latitude"), tmp.getDouble("longitude")));

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
