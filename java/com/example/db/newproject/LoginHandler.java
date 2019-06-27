package com.example.db.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginHandler extends AppCompatActivity {

   private EditText usernam,userpas;
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        usernam = findViewById(R.id.ln);
        userpas = findViewById(R.id.lp);
    progressDialog = new ProgressDialog(this);

    }

    public void loginUser(View view) {

        final String un = usernam.getText().toString().trim();
       final String up = userpas.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if(response.trim().charAt(0) == '[') {
                    Log.d("CODE",response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    User[] users = gson.fromJson(response, User[].class);
                    SharedPreferReference.getInstance(getApplicationContext())
                            .userLogin(
                                    users[0].getId(),
                                    users[0].getUsername(),
                                    users[0].getUserEmail()
                            );
                    finishAffinity();
                    startActivity(new Intent(getApplicationContext(), Profile_activity.class));

                } else if(response.trim().charAt(0) == '{') {
                    try{

                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(
                                getApplicationContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
         /*       try{

                    JSONObject obj = new JSONObject(response);

                    if (!obj.getBoolean("error")){

                        SharedPreferReference.getInstance(getApplicationContext())
                                .userLogin(
                                        obj.getInt("id"),
                                        obj.getString("username"),
                                        obj.getString("userEmail")
                                );

                        startActivity(new Intent(getApplicationContext(), Profile_activity.class));
                        finish();
                    }else{
                        Toast.makeText(
                                getApplicationContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                } */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", un);
                params.put("userPass", up);
                return params;
            }
        };
      RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}