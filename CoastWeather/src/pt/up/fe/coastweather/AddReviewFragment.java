package pt.up.fe.coastweather;

import pt.up.fe.coastweather.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddReviewFragment extends Fragment implements OnItemSelectedListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String LOG = "AddReviewFragment LOG";
	private CharSequence[] A = {"Praia da Rocha","Praia da Rocha1","Praia da Rocha2","Praia da Rocha3","Praia da Rocha4"};

	public AddReviewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_review,
				container, false);
		
		Spinner spinner = (Spinner) rootView.findViewById(R.id.beaches_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, A);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		
		spinner.setOnItemSelectedListener(this);
		
		ImageButton b1 = (ImageButton) rootView.findViewById(R.id.imagebutton1);
		b1.setSelected(true);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(LOG,"abc " + v.getId());
				v.setSelected(true);
			}
		});
		
		return rootView;
	}
	
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	Log.i(LOG, "Selected " + parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
