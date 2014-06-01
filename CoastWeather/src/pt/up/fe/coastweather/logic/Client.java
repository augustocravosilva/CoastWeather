package pt.up.fe.coastweather.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Client {
	public static final String GET_FRIENDS_ACT = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/status/user/friends/";
	public static String STATUS_BY_ID = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/status/";
	public static String GET_STATUS_OF_USER = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/status/user/";
	public static String POST_STATUS = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/status";
	public static String GET_BEACHES_BY_LOCATION= "http://coastweather.fe.up.pt/coastWeather/v1/index.php/beaches/";
	public static String GET_BEACHES = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/beaches";
	public static String GET_BEACH_BY_ID = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/beaches/";
	public static String GET_STATUS_BY_BEACH_ID = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/status/beach/";
	public static String POST_REGISTER = "http://coastweather.fe.up.pt/coastWeather/v1/index.php/users";

	public static String GET(String url, String data){
		InputStream inputStream = null;
		String result = "";

		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			HttpGet httpget = new HttpGet(url + data);
			httpget.setHeader("Authorization", genKey());

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpget);
			
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if(inputStream != null) 
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}
	
	final private static char[] hexArray = "0123456789abcdef".toCharArray();
	private static final String TAG = "clienthttp";
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static String genKey()
	{
		String out = "";
		String date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(new Date());
		String p1 = "20R0DJzwli";
		String pass = date + p1;
		//Log.d(TAG, "before-> "+pass);
		try {
			out = bytesToHex(MessageDigest.getInstance("SHA-256").digest(pass.getBytes()));
		} catch (NoSuchAlgorithmException e) {
		}
		Log.d(TAG,"key-> " + out);
		return out;
	}
	
	public static String DELETE(String url, String data){
		InputStream inputStream = null;
		String result = "";

		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			HttpDelete httpdelete = new HttpDelete(url + data);

			httpdelete.setHeader("Authorization",genKey());

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpdelete);
			
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if(inputStream != null) 
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	public static String POST(String url, List<NameValuePair> data){
		InputStream inputStream = null;
		String result = "";
		try {
			
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			
			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);
			
			
			// 6. set httpPost Entity
			httpPost.setEntity(new UrlEncodedFormEntity(data));
			
			// 7. Set some headers to inform server about the type of the content   
			httpPost.setHeader("Authorization", genKey());
			//httpPost.setHeader("Content-type", "application/json");
			
			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			
			// 10. convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
			
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		
		// 11. return result
		return result;
	}
	/*
	public static String POST(String url, JSONObject jsonObject){
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";



			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin Jackson Lib 
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person); 

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the content   
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		return result;
	}*/

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}   

}
