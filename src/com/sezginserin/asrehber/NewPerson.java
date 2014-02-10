package com.sezginserin.asrehber;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewPerson extends Activity {
	EditText name;
	EditText surname;
	EditText firm;
	EditText gsm;
	EditText email;
	DBController controller = new DBController(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_person);
		name = (EditText) findViewById(R.id.name);
		surname = (EditText) findViewById(R.id.surname);
		firm = (EditText) findViewById(R.id.firm);
		gsm = (EditText) findViewById(R.id.gsm);
		email = (EditText) findViewById(R.id.email);
	}

	public void addNewPerson(View view) {

		String emailString = email.getText().toString();
		String nameString = name.getText().toString();

		Validator v = new Validator();
		int validationResult = v.validate(nameString, emailString);
		if (validationResult == Validator.INVALID_NAME) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.name_is_empty), Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (validationResult == Validator.INVALID_EMAIL) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.email_not_valid), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		HashMap<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("name", name.getText().toString());
		queryValues.put("surname", surname.getText().toString());
		queryValues.put("firm", firm.getText().toString());
		queryValues.put("gsm", gsm.getText().toString());
		queryValues.put("email", email.getText().toString());
		controller.insertPerson(queryValues);
		this.callHomeActivity(view);
	}

	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivity(objIntent);
	}
}
