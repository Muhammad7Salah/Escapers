package com.example.ziko_.escapers;

//updated
import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class Welcome extends Activity {

    EditText username;
    EditText password;
    String s_username;
    String s_password;
    String url = "escape.6te.net/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void login(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_dialog);
        dialog.show();
        username=(EditText)dialog.findViewById(R.id.username_login);
        password=(EditText)dialog.findViewById(R.id.password_login);
    }
    public void signup(View view) {
        final Dialog dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.login_dialog);
        dialog1.show();

    }

    public void forgotPassword(View view) {
    }

    public void validate_login(View view) {
        Thread login =new Thread(new Runnable() {
            @Override
            public void run() {
                s_username=username.getText().toString();
                s_password=password.getText().toString();
                try{

                    // Creating HTTP client
                    HttpClient httpClient = new DefaultHttpClient();
                    // Creating HTTP Post
                    HttpPost httpPost = new HttpPost(url);
                    // Building post parameters
                    // key and value pair

                }
                catch (Exception e){
                    Log.i("escape",e.getMessage());
                }
            }
        });
    }
}
