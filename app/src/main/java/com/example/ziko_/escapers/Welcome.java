package com.example.ziko_.escapers;

import android.app.Dialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class Welcome extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText username_register;
    EditText email_register;
    EditText age_register;
    EditText password_register;
    String dbs_username;
    String dbs_password;
    String result = null;
    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
    String url="http://escape.6te.net/login.php";
    String url_signup="http://escape.6te.net/Registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void login(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_dialog);
        username=(EditText)dialog.findViewById(R.id.username_login);
        password=(EditText)dialog.findViewById(R.id.password_login);
        dialog.show();
        Button done_login=(Button)dialog.findViewById(R.id.done_login);
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
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.signup_dialog);
        username_register=(EditText)dialog.findViewById(R.id.username_signup);
        password_register=(EditText)dialog.findViewById(R.id.password_signup);
        email_register=(EditText)dialog.findViewById(R.id.email_signup);
        age_register=(EditText)dialog.findViewById(R.id.age_signup);
        dialog.show();
        Button done=(Button)dialog.findViewById(R.id.done_signup);
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
        final String reg_age = age_register.getText().toString();
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
                    nameValuePair.add(new BasicNameValuePair("Age", reg_age));
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
    }

    public void forgotPassword(View view) {
    }


    public void validate_login(final String s_username,final String s_password) {

        Thread login = new Thread(new Runnable() {


            @Override
            public void run() {

                try {
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

                    }

                    if (s_username.equals(dbs_username) && s_password.equals(dbs_password)) {

                        Intent intent = new Intent(Welcome.this, MapsActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(getApplicationContext(), "wrong email or password!", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.i("escape", "yanhaar  "+e.getMessage());
                }
            }
        });
        login.start();

    }

}
