package praekelt.weblistingapp.LoginArea;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import praekelt.weblistingapp.MainActivity;
import praekelt.weblistingapp.R;
import praekelt.weblistingapp.Rest.Models.ReceivedProfileData;
import praekelt.weblistingapp.Rest.Models.SentProfileData;
import praekelt.weblistingapp.Utils.Constants;
import retrofit.RestAdapter;

/**
 * Created by altus on 2015/04/16.
 */
public class CreateProfileActivity extends Activity{

    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private EditText password;
    private EditText confirmPassword;

    private EditText mobileNumber;

    private CheckBox receiveSms;
    private CheckBox receiveEmail;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);

        username = (EditText) findViewById(R.id.username);
        firstName = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);

        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);

        receiveSms = (CheckBox) findViewById(R.id.recieve_sms);
        receiveEmail = (CheckBox) findViewById(R.id.recieve_email);

        Button createProfile = (Button) findViewById(R.id.start_listening);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void sendData() {

        final String username = this.username.getText().toString();
        this.username.setError(null);

        String firstName = this.firstName.getText().toString();
        this.firstName.setError(null);

        String lastName = this.lastName.getText().toString();
        this.lastName.setError(null);

        String email = this.email.getText().toString();
        this.email.setError(null);

        final String password = this.password.getText().toString();
        this.password.setError(null);

        String confirmPassword = this.confirmPassword.getText().toString();
        this.confirmPassword.setError(null);

        String mobileNumber = this.mobileNumber.getText().toString();
        this.mobileNumber.setError(null);

        boolean receiveEmail = false;
        if(this.receiveEmail.isChecked()) {
            receiveEmail = true;
        }
        boolean receiveSms = false;
        if(this.receiveSms.isChecked()) {
            receiveSms = true;
        }

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            this.username.setError(getString(R.string.error_field_required));
            focusView = this.username;
            cancel = true;
        }
        if (TextUtils.isEmpty(firstName)) {
            this.firstName.setError(getString(R.string.error_field_required));
            focusView = this.firstName;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            this.lastName.setError(getString(R.string.error_field_required));
            focusView = this.lastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            this.password.setError(getString(R.string.error_field_required));
            focusView = this.password;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            this.confirmPassword.setError(getString(R.string.error_field_required));
            focusView = this.confirmPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            this.confirmPassword.setError(getString(R.string.error_field_required));
            focusView = this.confirmPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            this.mobileNumber.setError(getString(R.string.error_field_required));
            focusView = this.mobileNumber;
            cancel = true;
        }

        boolean noMatch = false;
        if (!(confirmPassword.equals(password))) {
            alertDialogue(getString(R.string.error_passwords_no_match));
            noMatch = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
                focusView.requestFocus();
        } else if(!noMatch){
            RestAdapter restAdapter = new RestAdapter.Builder()
                        //.setEndpoint(Constants.JAC_BASE_URL)
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();

            final SentProfileData profile = new SentProfileData();
            profile.setUsername(username);
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setEmail(email);
            profile.setMobileNumber(mobileNumber.replaceAll("\\s", ""));
            profile.setPassword(password);
            profile.setReceiveEmail(receiveEmail);
            profile.setReceiveSms(receiveSms);
        }
    }

    private void alertDialogue(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void launchMain(ReceivedProfileData profile) {
        Intent myIntent = new Intent(CreateProfileActivity.this, MainActivity.class);
        myIntent.putExtra("profileData", profile); //Optional parameters
        CreateProfileActivity.this.startActivity(myIntent);
        finish();
    }
}



