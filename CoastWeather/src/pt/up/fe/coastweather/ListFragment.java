package pt.up.fe.coastweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list,
				container, false);

		list = (ListView) rootView.findViewById(R.id.MessageList);
		list.setAdapter(new ListFragmentAdapter(getActivity()));

		return rootView;
	}
}
