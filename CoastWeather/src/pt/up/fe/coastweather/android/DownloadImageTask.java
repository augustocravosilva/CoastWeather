package pt.up.fe.coastweather.android;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageTask(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			Log.d("Down",urls[0]);
			URLConnection con = new URL(urldisplay).openConnection();
			con.connect();
			InputStream in2 = con.getInputStream();
			//TODO this is doing 2 requests, only way I could make it work
			//because of redirects
			InputStream in = new java.net.URL(con.getURL().toString()).openStream();
			in2.close();
			if (in != null) //added because sometimes it appear to be null
				mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}