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

public class ListFragment extends Fragment {

	
	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final int NUMBER_BEACHES = 30;


	ListView list;
	ListFragmentAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list,
				container, false);

		list = (ListView) rootView.findViewById(R.id.MessageList);
		adapter = new ListFragmentAdapter(getActivity(), NUMBER_BEACHES);
		
		list.setAdapter(adapter);
		adapter.updateData();
		

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(@SuppressWarnings("rawtypes") AdapterView a, View v, int position, long id) {
				if(adapter != null) {
					Intent x = new Intent(getActivity(), BeachActivity.class);
					x.putExtra(BeachActivity.BEACH_ID, adapter.getItem(position).getIdBeach());
					startActivity(x);
				}
			}});

		return rootView;

	}

	
}
