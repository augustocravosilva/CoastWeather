package pt.up.fe.coastweather.android;

import java.util.Arrays;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import pt.up.fe.coastweather.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	private static final String TAG = "LoginFragment";
	private GraphUser fbuser;
	
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login,
				container, false);
		text = (TextView) view
				.findViewById(R.id.username);
		text.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));
		authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("public_profile","user_friends"));
		authButton.setFragment(this);
		return view;
	}
	
	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		this.msession=session;
	        if (state.isOpened()) {
		        Log.i(TAG, "Logged in...");


	            // Request user data and show the results
	            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

	                @Override
	                public void onCompleted(GraphUser user, Response response) {
	                    if (user != null) {
	                        // Display the parsed user info
	                       fbuser = user;
	                       text.setText(user.getName() + " "+user.getId() + " " + response);
	                       new Request(
	                    		    msession,
	                    		    "/me/friends",
	                    		    null,
	                    		    HttpMethod.GET,
	                    		    new Request.Callback() {
	                    		        public void onCompleted(Response response) {
	                    		            text.append("\n\n\n"+response.toString());
	                    		        }
	                    		    }
	                    		).executeAsync();
	                    }
	                }
	            });
	        }
	   else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        text.setText("logged out");
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
}