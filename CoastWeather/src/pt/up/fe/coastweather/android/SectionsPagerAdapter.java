package pt.up.fe.coastweather.android;

import java.util.Locale;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.User;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	private static final String TAG = "SectionsPagerAdapter";
	private Context context;
	private LoginFragment login;
	private Fragment mapFragment = new MapFragment();

	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:
		{
			return mapFragment;
		}
		case 1: {
			ListFragment fragment = new ListFragment();
			Bundle args = new Bundle();
			args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}
		case 3: {
			if(User.getInstance().isLoggedIn())
			{
				AddReviewFragment fragment = new AddReviewFragment();
				Bundle args = new Bundle();
				args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			login = new LoginFragment();
			Bundle args = new Bundle();
			args.putInt(LoginFragment.ARG_SECTION_NUMBER, position + 1);
			login.setArguments(args);
			login.setAdapter(this);
			return login;
		}
		case 2: {
			if(User.getInstance().isLoggedIn())
			{
				FriendsFragment fragment = new FriendsFragment();
				Bundle args = new Bundle();
				args.putInt(LoginFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			login = new LoginFragment();
			Bundle args = new Bundle();
			args.putInt(LoginFragment.ARG_SECTION_NUMBER, position + 1);
			login.setArguments(args);
			login.setAdapter(this);
			return login;
		}
		case 4: {
			if(User.getInstance().isLoggedIn())
			{
				MineFragment fragment = new MineFragment();
				Bundle args = new Bundle();
				args.putInt(MineFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			login = new LoginFragment();
			Bundle args = new Bundle();
			args.putInt(LoginFragment.ARG_SECTION_NUMBER, position + 1);
			login.setArguments(args);
			login.setAdapter(this);
			return login;
		}
		default: {
			return null;
		}
		}
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 5;
	}
	
	@Override
	public int getItemPosition (Object object) // this determines if the fragments changed
	{ // useful to use with notifyDataSetChanged
		Log.d(TAG,"NOFITY!!");
		if (object instanceof LoginFragment || object instanceof FriendsFragment || object instanceof MineFragment) {
			return POSITION_NONE;
		} else return POSITION_UNCHANGED;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_section2).toUpperCase(l);
		case 2:
			return context.getString(R.string.title_section3).toUpperCase(l);
		case 3:
			return context.getString(R.string.title_section4).toUpperCase(l);
		case 4:
			return context.getString(R.string.title_section5).toUpperCase(l);
		}
		return null;
	}
}