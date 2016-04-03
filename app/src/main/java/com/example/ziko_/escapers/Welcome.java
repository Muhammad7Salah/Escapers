package com.example.ziko_.escapers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Welcome extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    EditText username;
    EditText password;
    EditText username_register;
    EditText email_register;
    EditText age_register;
    EditText password_register;
    NumberPicker numberPicker;
    String dbs_username;
    String dbs_password;
    String result = null;
    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
    String url="http://escape.6te.net/login.php";
    String url_signup="http://escape.6te.net/Registration.php";
    SignInButton google_signin;
    GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;
    private ViewFlipper viewFlipper;
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fliper);
        viewFlipper=(ViewFlipper) findViewById(R.id.view_flipper);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        google_signin=(SignInButton)findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        google_signin.setSize(google_signin.SIZE_WIDE);
        google_signin.setScopes(gso.getScopeArray());
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    @Override
    public void onBackPressed () {
        if (viewFlipper.getDisplayedChild() == 0) {
            super.onBackPressed();
        }
        if(viewFlipper.getDisplayedChild()==1){
            viewFlipper.setInAnimation(this,R.anim.in_from_left);
            viewFlipper.setOutAnimation(this, R.anim.out_to_right);
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.welcome)));
        }
        if (viewFlipper.getDisplayedChild() == 2) {
            viewFlipper.setInAnimation(this, R.anim.in_from_right);
            viewFlipper.setOutAnimation(this, R.anim.out_to_left);
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.welcome)));

        }
    }


    public void login(View view) {
        /*final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_dialog);
        */
        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.dialog_login)));
        username=(EditText) findViewById(R.id.username_login);
        password=(EditText) findViewById(R.id.password_login);
        //dialog.show();
        Button done_login=(Button) findViewById(R.id.done_login);
        done_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ss_username = username.getText().toString();
                final String ss_password = password.getText().toString();
                if (ss_username.matches("") && ss_password.matches("")) {

                    Toast.makeText(getApplicationContext(), "Please, fill the fields", Toast.LENGTH_SHORT).show();
                } else if (ss_username.matches("")) {

                    Toast.makeText(getApplicationContext(), "Please, Enter User Name!", Toast.LENGTH_SHORT).show();

                } else if (ss_password.matches("")) {

                    Toast.makeText(getApplicationContext(), "Please, Enter the Password!", Toast.LENGTH_SHORT).show();

                }else {
                    validate_login(ss_username, ss_password);
                }

                Log.i("escape", "id: " + dbs_username + ", password " + dbs_password);
            }
        });

    }



    public void signup(final View view) {
        /*final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.signup_dialog);
        */
        viewFlipper.setInAnimation(this, R.anim.in_from_left);
        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.dialog_signup)));
        username_register=(EditText) findViewById(R.id.username_signup);
        password_register= (EditText) findViewById(R.id.password_signup);
        email_register=(EditText) findViewById(R.id.email_signup);
        //age_register=(EditText) findViewById(R.id.age_signup);
        /*numberPicker=(NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);// restricted number to minimum value i.e 1
        numberPicker.setMaxValue(100);// restricked number to maximum value i.e. 31
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setBackgroundColor(Color.YELLOW);
*/
        final String R_username = username_register.getText().toString();
        final String R_password = password_register.getText().toString();
        final String R_email = email_register.getText().toString();
//        final String R_age = age_register.getText().toString();
        // dialog.show();
        Button done=(Button) findViewById(R.id.done_signup);
        if(R_email.equals("")||R_username.equals("")|| R_password.equals(""))
            Toast.makeText(getApplicationContext(),"please,Enter all the fields!",Toast.LENGTH_SHORT).show();
        
        else
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

    }

    private void register() {
        final String reg_username = username_register.getText().toString();
        final String reg_email= email_register.getText().toString();
        final String reg_password = password_register.getText().toString();
        //int number= numberPicker.getValue();
       // final String reg_age= Integer.toString(number);
        Thread register = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Creating HTTP client
                    HttpClient httpClient = new DefaultHttpClient();
                    // Creating HTTP Post
                    HttpPost httpPost = new HttpPost(url_signup);
                    // Building post parameters
                    // key and value pair
                    nameValuePair.add(new BasicNameValuePair("UserName", reg_username));
                    //nameValuePair.add(new BasicNameValuePair("Age", reg_age));
                    nameValuePair.add(new BasicNameValuePair("Email", reg_email));
                    nameValuePair.add(new BasicNameValuePair("Password", reg_password));

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    }
                catch (Exception e){
                    Log.i("escape" , e.getMessage());           }
            }
        });
        register.start();
        Log.i("escape",reg_username);
        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.welcome)));

    }

    public void forgotPassword(View view) {
    }


    public void validate_login(final String s_username,final String s_password) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please Wait .");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        Thread login = new Thread(new Runnable() {


            @Override
            public void run() {

                try {
/*
                    // Creating HTTP client
                    HttpClient httpClient = new DefaultHttpClient();
                    // Creating HTTP Post
                    HttpPost httpPost = new HttpPost(url);
                    // Building post parameters
                    // key and value pair
                    nameValuePair.add(new BasicNameValuePair("UserName", s_username));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    result = EntityUtils.toString(entity);

                    Log.i("escape", result.toString()+"1111");

                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        dbs_username = jsonObject.getString("UserName");
                        dbs_password = jsonObject.getString("Password");

                    }*/

                    if (s_username.equals("m") && s_password.equals("1")) {

                        Intent intent = new Intent(Welcome.this,MapsActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(getApplicationContext(), "wrong email or password!", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.i("escape",e.getMessage());
                }
            }
        });
        login.start();

    }
    // GOOGLE Login
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("memo", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount google_account = result.getSignInAccount();
            String google_name=google_account.getDisplayName();
            String google_email=google_account.getEmail();
            String google_id=google_account.getId();
            Intent intent = new Intent(Welcome.this,Profile.class);
            intent.putExtra("name",google_name);
            intent.putExtra("email",google_email);
            intent.putExtra("id",google_id);
            startActivity(intent);
        } else {
            //updateUI(false);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //GO To Sign in by Email
        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.dialog_login)));
    }
}
