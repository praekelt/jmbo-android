package praekelt.weblistingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import praekelt.weblistingapp.Rest.Models.ReceivedProfileData;
import praekelt.weblistingapp.Rest.Models.SentProfileData;
import praekelt.weblistingapp.Utils.Constants;
import retrofit.RestAdapter;


public class UpdateProfileActivity extends Activity {

    private EditText username;
    private EditText password;
    private EditText email;

    private EditText mobileNumber;

    private CheckBox receiveSms;
    private CheckBox receiveEmail;

    ReceivedProfileData profileGlobal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getSerializableExtra("profileData") != null) {
            profileGlobal = (ReceivedProfileData) getIntent().getSerializableExtra("profileData");
            Log.d("Profile Username", profileGlobal.getUsername());
        }

        setContentView(R.layout.activity_update_profile);

        username = (EditText) findViewById(R.id.username);
        username.setText(this.profileGlobal.getUsername());

        password = (EditText) findViewById(R.id.password);

        email = (EditText) findViewById(R.id.email);
        email.setText(this.profileGlobal.getEmail());

        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        mobileNumber.setText(this.profileGlobal.getMobileNumber());

        receiveSms = (CheckBox) findViewById(R.id.recieve_sms);
        receiveEmail = (CheckBox) findViewById(R.id.recieve_email);

        Button createProfile = (Button) findViewById(R.id.update_profile);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendData();
            }
        });

        Button signOut = (Button) findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                returnToMain(null);
            }
        });
    }

    private void sendData() {

        final String username = this.username.getText().toString();
        this.username.setError(null);

        final String password = this.password.getText().toString();
        this.password.setError(null);

        String email = this.email.getText().toString();
        this.email.setError(null);

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
        if (TextUtils.isEmpty(password)) {
            this.password.setError(getString(R.string.error_field_required));
            focusView = this.password;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            this.mobileNumber.setError(getString(R.string.error_field_required));
            focusView = this.mobileNumber;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    //.setEndpoint(Constants.JAC_BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            final SentProfileData profile = new SentProfileData();
            profile.setUsername(username);
            profile.setFirstName(this.profileGlobal.getFirstName());
            profile.setLastName(this.profileGlobal.getLastName());
            profile.setEmail(email);
            profile.setMobileNumber(mobileNumber.replaceAll("\\s", ""));
            profile.setReceiveEmail(receiveEmail);
            profile.setReceiveSms(receiveSms);
        }
    }

    public void alertDialogue(String message){
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

    private void returnToMain(ReceivedProfileData profile) {
        Intent intent = new Intent();
        intent.putExtra("profileData",profile);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
