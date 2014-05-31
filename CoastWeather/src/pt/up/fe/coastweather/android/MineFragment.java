package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.Client;
import pt.up.fe.coastweather.logic.User;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MineFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "mine-frag";
	ListView list;
	MineFragmentAdapter lfa;
	TextView numbersView;
	View separator;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mine,
				container, false);
		
		ImageView image = (ImageView) rootView.findViewById(R.id.icon);
		TextView nameView = (TextView) rootView.findViewById(R.id.name);
		separator = rootView.findViewById(R.id.mine_separator);
		numbersView = (TextView) rootView.findViewById(R.id.numbers);
		nameView.setText(User.getInstance().getName());
		new DownloadImageTask(image).execute(User.getUserPicLinkMedium(String.valueOf(User.getInstance().getFacebookId())));

		list = (ListView) rootView.findViewById(R.id.MineList);
		lfa = new MineFragmentAdapter(getActivity(),this);
		list.setAdapter(lfa);
		lfa.updateData();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView a, View v, int position, long id) {
				Log.d(TAG,"click beach!");
				Intent x = new Intent(getActivity(), BeachActivity.class);
				x.putExtra(BeachActivity.BEACH_ID, lfa.getItem(position).getBeachId());
				startActivity(x);
			}});

		return rootView;

	}
	public void setNumberStatus(int size) {
		if(size==1)
			numbersView.setText(" " + size + " review");
		else 
			numbersView.setText(" " + size + " reviews");
		Log.d(TAG,"set done");
		if(size>0)
			separator.setVisibility(View.VISIBLE);
		else separator.setVisibility(View.GONE);
	}
}
