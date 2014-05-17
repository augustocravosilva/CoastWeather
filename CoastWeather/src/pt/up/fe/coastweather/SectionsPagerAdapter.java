package pt.up.fe.coastweather;

import java.util.Locale;

import pt.up.fe.coastweather.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	private LoginFragment login;
	private Fragment mapFragment = new MapFragment();

	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		/*Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;*/

		switch (position) {
		case 1: {
			ListFragment fragment = new ListFragment();
			Bundle args = new Bundle();
			args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}
		case 3: {
			AddReviewFragment fragment = new AddReviewFragment();
			Bundle args = new Bundle();
			args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		} case 2: {
			//if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			login = new LoginFragment();
			/*  ((FragmentActivity) context).getSupportFragmentManager()
		        .beginTransaction()
		        .add(login,"LOGIN")
		        .commit();*/
			// } else {
			// Or set the fragment from restored state info
			//  	login = (LoginFragment) ((FragmentActivity) context).getSupportFragmentManager()
			//        .findFragmentByTag("LOGIN");
			//    }
			Bundle args = new Bundle();
			args.putInt(LoginFragment.ARG_SECTION_NUMBER, position + 1);
			login.setArguments(args);
			return login;
		}
		/*case 0: {
			DummySectionFragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}*/
		default: {
			return mapFragment;
		}
		}
		/*	if(position == 3) {
			AddReviewFragment fragment = new AddReviewFragment();
			Bundle args = new Bundle();
			args.putInt(AddReviewFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}
		else {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}*/
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 4;
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
		}
		return null;
	}
}