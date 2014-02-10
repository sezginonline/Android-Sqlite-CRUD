package com.sezginserin.asrehber;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPerson extends Activity {
	TextView name;
	TextView surname;
	TextView firm;
	TextView gsm;
	TextView email;
	String personId;
	DBController controller = new DBController(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_person);
		name = (TextView) findViewById(R.id.name);
		surname = (TextView) findViewById(R.id.surname);
		firm = (TextView) findViewById(R.id.firm);
		gsm = (TextView) findViewById(R.id.gsm);
		email = (TextView) findViewById(R.id.email);
		Intent objIntent = getIntent();
		personId = objIntent.getStringExtra("id");
		HashMap<String, String> personList = controller.getPersonInfo(personId);
		if (personList.size() != 0) {
			name.setText(personList.get("name"));
			surname.setText(personList.get("surname"));
			firm.setText(personList.get("firm"));
			gsm.setText(personList.get("gsm"));
			email.setText(personList.get("email"));
		}
	}

	public void editPerson(View view) {
		Intent objIndent = new Intent(getApplicationContext(), EditPerson.class);
		objIndent.putExtra("id", personId);
		startActivity(objIndent);
	}

	public void removePerson(View view) {

		new AlertDialog.Builder(DetailPerson.this)
				.setMessage(getString(R.string.confirm_delete))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent objIntent = getIntent();
								String personId = objIntent
										.getStringExtra("id");
								controller.deletePerson(personId);
								Intent intent = new Intent(
										getApplicationContext(),
										MainActivity.class);
								startActivity(intent);
							}
						}).setNegativeButton(getString(R.string.no), null)
				.show();

	}

	public void sendSMS(View view) {

		if (gsm.getText().toString().matches("")) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.gsm_is_empty), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
				+ gsm.getText()));
		// intent.putExtra("sms_body", "test");
		startActivity(intent);
	}
}
