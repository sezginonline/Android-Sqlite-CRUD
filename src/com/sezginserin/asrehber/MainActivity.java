package com.sezginserin.asrehber;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	Intent intent;
	TextView txtId;
	EditText searchText;
	DBController controller = new DBController(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextWatcher inputTextWatcher = new TextWatcher() {
			public void afterTextChanged(Editable s) {

				ArrayList<HashMap<String, String>> personList;

				if (s.toString().matches("")) {
					personList = controller.getAllPersons();
				} else {
					personList = controller.searchPerson(s.toString());
				}

				setList(personList);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		};

		searchText = (EditText) findViewById(R.id.editText1);
		searchText.addTextChangedListener(inputTextWatcher);

		ArrayList<HashMap<String, String>> personList = controller
				.getAllPersons();
		setList(personList);
	}

	public void setList(ArrayList<HashMap<String, String>> personList) {
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				txtId = (TextView) view.findViewById(R.id.id);
				String valPersonId = txtId.getText().toString();
				Intent objIndent = new Intent(getApplicationContext(),
						DetailPerson.class);
				objIndent.putExtra("id", valPersonId);
				startActivity(objIndent);
			}
		});
		ListAdapter adapter = new SimpleAdapter(MainActivity.this, personList,
				R.layout.view_person_entry, new String[] { "id", "name",
						"surname" }, new int[] { R.id.id, R.id.name,
						R.id.surname });
		setListAdapter(adapter);
	}

	public void showAddForm(View view) {
		Intent objIntent = new Intent(getApplicationContext(), NewPerson.class);
		startActivity(objIntent);
	}
}
