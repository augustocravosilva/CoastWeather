package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FriendsFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	ListView list;
	FriendsFragmentAdapter lfa;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friends,
				container, false);

		list = (ListView) rootView.findViewById(R.id.MessageList);
		lfa = new FriendsFragmentAdapter(getActivity());
		list.setAdapter(lfa);
		lfa.updateData();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView a, View v, int position, long id) {
				Intent x = new Intent(getActivity(), BeachActivity.class);
				x.putExtra(BeachActivity.BEACH_ID, lfa.getItem(position).getBeachId());
				startActivity(x);
			}});

		return rootView;

	}
}
