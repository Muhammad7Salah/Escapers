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
    String dbs_username;
    String dbs_password;
    String result = null;
    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
    String url="http://escape.6te.net/login.php";

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
        Button done=(Button)dialog.findViewById(R.id.done_login);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_login();
            }
        });

    }
    public void signup(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.signup_dialog);
        dialog.show();

    }

    public void forgotPassword(View view) {
    }


    public void validate_login() {
        final String s_username = username.getText().toString();
        final String s_password = password.getText().toString();
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
                    nameValuePair.add(new BasicNameValuePair("Password", s_password));

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    result = EntityUtils.toString(entity);

                    Log.i("escape", result.toString());

                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        dbs_username = jsonObject.getString("UserName");
                        dbs_password = jsonObject.getString("Password");

                    }

                } catch (Exception e) {
                    Log.i("escape", e.getMessage());
                }
            }
        });
        login.start();
        if (s_username.matches("") && s_password.matches("")) {

            Toast.makeText(getApplicationContext(), "Please, fill the fields", Toast.LENGTH_SHORT).show();
        } else if (s_username.matches("")) {

            Toast.makeText(getApplicationContext(), "Please, Enter User Name!", Toast.LENGTH_SHORT).show();

        } else if (password.getText().toString().matches("")) {

            Toast.makeText(getApplicationContext(), "Please, Enter the Password!", Toast.LENGTH_SHORT).show();

        } else if (s_username.equals(dbs_username) && s_password.equals(dbs_password)) {
            Log.i("memo", "id: " + dbs_username + ", password " + dbs_password);
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);

        } else {

            Toast.makeText(getApplicationContext(), "wrong email or password!", Toast.LENGTH_SHORT).show();

        }

    }
}
