package pt.up.fe.coastweather.android;

import pt.up.fe.coastweather.R;
import pt.up.fe.coastweather.logic.*;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddReviewFragment extends Fragment implements OnItemSelectedListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String LOG = "CoastWeather";

	private CharSequence[] A = {"Praia da Rocha","Praia da Rocha1","Praia da Rocha2","Praia da Rocha3","Praia da Rocha4"};
	private int[] B = {1,2,3,4,5};


	private ImageButton[] feelingButtons = new ImageButton[5];
	private ImageButton[] weatherButtons = new ImageButton[4];
	private ImageButton[] flagsButtons = new ImageButton[4];

	private Button shareButton;

	private TextView feelingText;
	private TextView test;
	private Spinner spinner;
	private UserStatus user = null;


	public AddReviewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_review,
				container, false);

		spinner = (Spinner) rootView.findViewById(R.id.beaches_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, A);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//spinner.setAdapter(adapter);


		spinner.setOnItemSelectedListener(this);

		feelingButtons[0] = (ImageButton) rootView.findViewById(R.id.imagebuttonM1);
		feelingButtons[1] = (ImageButton) rootView.findViewById(R.id.imagebuttonM2);
		feelingButtons[2] = (ImageButton) rootView.findViewById(R.id.imagebutton0);
		feelingButtons[3] = (ImageButton) rootView.findViewById(R.id.imagebutton1);
		feelingButtons[4] = (ImageButton) rootView.findViewById(R.id.imagebutton2);

		weatherButtons[0] = (ImageButton) rootView.findViewById(R.id.imagebuttonSunny);
		weatherButtons[1] = (ImageButton) rootView.findViewById(R.id.imagebuttonWindy);
		weatherButtons[2] = (ImageButton) rootView.findViewById(R.id.imagebuttonCloudy);
		weatherButtons[3] = (ImageButton) rootView.findViewById(R.id.imagebuttonRainy);

		flagsButtons[0] = (ImageButton) rootView.findViewById(R.id.imagebuttonGreen);
		flagsButtons[1] = (ImageButton) rootView.findViewById(R.id.imagebuttonYellow);
		flagsButtons[2] = (ImageButton) rootView.findViewById(R.id.imagebuttonRed);
		flagsButtons[3] = (ImageButton) rootView.findViewById(R.id.imagebuttonBlack);

		feelingText = (TextView) rootView.findViewById(R.id.feelingtext);
		test = (TextView) rootView.findViewById(R.id.textTest);


		for(ImageButton i : feelingButtons) {
			i.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					unselectOtherButtons();
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



				private void unselectOtherButtons() {
					for(ImageButton j : feelingButtons)
						j.setSelected(false);
				}
			});
		}

		for(ImageButton i : weatherButtons) {
			i.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean selected = v.isSelected();

					if(!((String)v.getContentDescription()).equals("windy"))
						unselectOtherButtons();
					v.setSelected(!selected);
				}

				private void unselectOtherButtons() {
					for(ImageButton j : weatherButtons) {
						if (!((String)j.getContentDescription()).equals("windy"))
							j.setSelected(false);
					}

				}
			});
		}

		for(ImageButton i : flagsButtons) {
			i.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean selected = v.isSelected();

					unselectOtherButtons();
					v.setSelected(!selected);
				}

				private void unselectOtherButtons() {
					for(ImageButton j : flagsButtons) {
						j.setSelected(false);
					}

				}
			});
		}
		shareButton = (Button) rootView.findViewById(R.id.button_publish);

		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(LOG, "handler");
				int feeling = getFeeling();

				int flag = getFlag();

				boolean sunny = weatherButtons[0].isSelected();
				boolean windy = weatherButtons[1].isSelected();
				boolean cloudy = weatherButtons[2].isSelected();
				boolean rainy = weatherButtons[3].isSelected();

				int beachId = B[spinner.getSelectedItemPosition()];

				if(feeling < 0 || !(sunny || windy || cloudy || rainy)) {
					Toast.makeText(getActivity(), "Select it!", Toast.LENGTH_SHORT).show();
					return;
				}

				user = new UserStatus(beachId, feeling, flag, sunny, windy, cloudy, rainy);


				new HttpAsyncTask().execute(Client.POST_STATUS);

			}

			private int getFlag() {
				for(int i = 0; i < flagsButtons.length; i++)
					if(flagsButtons[i].isSelected())
						return i;
				return -1;
			}

			private int getFeeling() {
				for(int i = 0; i < feelingButtons.length; i++)
					if(feelingButtons[i].isSelected())
						return i;

				Toast.makeText(getActivity(), "You must select a feeling!", Toast.LENGTH_SHORT).show(); //TODO: add to strings
				return -1;
			}
		});

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

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			if (user != null)
				return Client.POST(urls[0],user.getPost());
			else
				return "";
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			Toast.makeText(getActivity(), "Data Sent!", Toast.LENGTH_SHORT).show();
			test.setText(result);
		}
	}
}
