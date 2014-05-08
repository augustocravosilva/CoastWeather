package pt.up.fe.coastweather;

import pt.up.fe.coastweather.R;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddReviewFragment extends Fragment implements OnItemSelectedListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String LOG = "AddReviewFragment LOG";

	private CharSequence[] A = {"Praia da Rocha","Praia da Rocha1","Praia da Rocha2","Praia da Rocha3","Praia da Rocha4"};


	private ImageButton[] feelingButtons = new ImageButton[5];
	private TextView feelingText;


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

		feelingButtons[0] = (ImageButton) rootView.findViewById(R.id.imagebuttonM1);
		feelingButtons[1] = (ImageButton) rootView.findViewById(R.id.imagebuttonM2);
		feelingButtons[2] = (ImageButton) rootView.findViewById(R.id.imagebutton0);
		feelingButtons[3] = (ImageButton) rootView.findViewById(R.id.imagebutton1);
		feelingButtons[4] = (ImageButton) rootView.findViewById(R.id.imagebutton2);

		feelingText = (TextView) rootView.findViewById(R.id.feelingtext);


		for(ImageButton i : feelingButtons) {
			i.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					unselectOtherButtons(v.getId());
					v.setSelected(true);
					setFeelingText(((String)v.getContentDescription()).trim());
				}



				private void setFeelingText(String val) {
					int i = Integer.parseInt(val);
					Log.i(LOG, "-->" + i + "<--");
					switch(i) {
					case -2:
						feelingText.setText(getResources().getString(R.string.feeling_m2_text));
						break;
					case -1:
						feelingText.setText(getResources().getString(R.string.feeling_m1_text));
						break;
					case 0:
						feelingText.setText(getResources().getString(R.string.feeling_0_text));
						break;
					case 1:
						feelingText.setText(getResources().getString(R.string.feeling_1_text));
						break;
					case 2:
						feelingText.setText(getResources().getString(R.string.feeling_2_text));
						break;
					}
				}



				private void unselectOtherButtons(int id) {
					for(ImageButton j : feelingButtons)
						j.setSelected(false);
				}
			});
		}
		
		/*ImageButton b1 = (ImageButton) rootView.findViewById(R.id.imagebutton1);
		b1.setSelected(true);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(LOG,"abc " + v.getId());
				v.setSelected(true);
			}
		});*/

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
