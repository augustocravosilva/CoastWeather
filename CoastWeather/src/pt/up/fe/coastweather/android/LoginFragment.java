package pt.up.fe.coastweather.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Response.PagingDirection;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Client;
import pt.up.fe.coastweather.logic.User;
import pt.up.fe.coastweather.logic.UserStatus;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class LoginFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final String TAG = "LoginFragment ";
	private GraphUser fbuser;
	private SectionsPagerAdapter spa;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	private UiLifecycleHelper uiHelper;
	private LoginButton authButton;
	private TextView text;
	private Session msession;

	public LoginFragment() {
		super();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login,
				container, false);
		text = (TextView) view
				.findViewById(R.id.username);
		//text.setText(Integer.toString(getArguments().getInt(
			//	ARG_SECTION_NUMBER)));
		authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("public_profile","user_friends","email"));
		authButton.setFragment(this);
		return view;
	}

	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		this.msession=session;
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			text.setText("Loading...");
			authButton.setVisibility(View.GONE);

			// Request user data and show the results
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						// Display the parsed user info
						fbuser = user;
						User.getInstance().setFacebookId(user.getId());
						User.getInstance().setName(user.getName());
						Object email = user.getProperty("email");
						String emailstr = "no-email@no-email.com";
						if(email != null)
							emailstr = email.toString();
						Log.d(TAG,response.toString());
						new HttpAsyncTask().execute(user.getId(),user.getName(),emailstr);
						//text.setText(user.getName() + " "+user.getId() + " " + response);
						new Request(
								msession,
								"/me/friends",
								null,
								HttpMethod.GET,
								new Request.Callback() {
									public void onCompleted(Response response) {
										//  text.append("\n\n\n"+response.getGraphObject().getPropertyAsList("data",GraphUser.class));
										GraphObjectList<GraphUser> glist = response.getGraphObject().getPropertyAsList("data",GraphUser.class);
										if(glist==null)
											return;
										for(GraphUser friend : glist)
										{
											String fid = friend.getId();
											String fname = friend.getName();
											User.getInstance().addFriend(fid,fname);
										}

										Request r2 = response.getRequestForPagedResults(PagingDirection.NEXT);
										if(r2!=null)
										{
											r2.setCallback(this);
											r2.executeAsync();
										} else
										{
											spa.notifyDataSetChanged();

										}
									}
								}
								).executeAsync();
					}
				}
			});
		}
		else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
			//text.setText("logged out");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	public void setAdapter(SectionsPagerAdapter sectionsPagerAdapter) {
		spa = sectionsPagerAdapter;
	}


	private class HttpAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				ArrayList<UserStatus> statusArray = new ArrayList<UserStatus>();
				String list = User.getInstance().friendsArrayAsString();
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("idFacebook",params[0]));
				data.add(new BasicNameValuePair("name",params[1]));
				data.add(new BasicNameValuePair("email",params[2]));
				String out = Client.POST(Client.POST_REGISTER, data);
				Log.d(TAG,out);
				String error = new JSONObject(out).get("error").toString();
				if(error == "true" && (new JSONObject(out)).get("message").toString().contains("id"))
					return true;
			} catch (Exception e) {
				Log.w(TAG, "json  " + e.getMessage());
			}
			Log.e(TAG, "Not able to register");
			return false;
		}

	}
}