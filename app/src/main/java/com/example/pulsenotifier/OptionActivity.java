package com.example.pulsenotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        // -- set the edit texts' values to be what is already saved into the shared preferences --

        // get tje edit texts
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText birthdayEditText = findViewById(R.id.birthdayEditText);
        EditText phoneEditText = findViewById(R.id.emergencyPhoneEditText);

        // get the saved preferences
        String prefsName = getString(R.string.preferences_name);
        SharedPreferences prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);

        String name = prefs.getString(getString(R.string.name_pref_name), null);
        String bdate = prefs.getString(getString(R.string.bday_pref_name), null);
        String phone = prefs.getString(getString(R.string.phone_pref_name), null);

        if (name != null)
            nameEditText.setText(name);
        if (bdate != null)
            birthdayEditText.setText(bdate);
        if (phone != null)
            phoneEditText.setText(phone);
    }


    public void onSubmitClick(View view) {
        // make sure we were clicked but the submit button
        if (view.getId() == R.id.submitButton) {
            // get all the edit texts
            EditText nameEditText = findViewById(R.id.nameEditText);
            EditText birthdayEditText = findViewById(R.id.birthdayEditText);
            EditText phoneEditText = findViewById(R.id.emergencyPhoneEditText);

            // get the values of the edit texts
            String name = nameEditText.getText().toString();
            String bday = birthdayEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            // get the shared prefs to save into them the changes
            String prefsName = getString(R.string.preferences_name);
            SharedPreferences prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // save the changes
            editor.putString(getString(R.string.name_pref_name), name);
            editor.putString(getString(R.string.bday_pref_name), bday);
            editor.putString(getString(R.string.phone_pref_name), phone);
            editor.apply();
        }
    }

    public void onReturnClick(View view) {
        // make sure we were clicked but the return button
        if (view.getId() == R.id.returnButton) {
            // return to main activity

            // DON'T KNOW IF IT WORKS. FOUND IT ON STACK OVERFLOW:
            // https://stackoverflow.com/questions/24610527/how-do-i-get-a-button-to-open-another-activity
            startActivity(new Intent(OptionActivity.this, MainActivity.class));
        }
    }
}