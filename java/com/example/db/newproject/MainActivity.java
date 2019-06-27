package com.example.db.newproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

   private EditText username,useremail,userpass;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPreferReference.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Profile_activity.class));
        }

    username = findViewById(R.id.un);
    useremail = findViewById(R.id.ue);
    userpass = findViewById(R.id.up);

        progressDialog = new ProgressDialog(this);
    }

    public void registerUser(View view) {
        final String userN = username.getText().toString().trim();
        final String userE = useremail.getText().toString().trim();
        final String userP = userpass.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
              //  Toast.makeText(MainActivity.this,"SUCCEED",Toast.LENGTH_SHORT).show();
                  try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",userN);
                params.put("userEmail",userE);
                params.put("userPass",userP);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this,LoginHandler.class);
        startActivity(intent);
       // finish();
    }
}
