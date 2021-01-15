package com.example.exam;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Adapters.ProductAdapter;
import com.example.model.Product;
import com.example.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class login extends AppCompatActivity {
    //the URL having the json data
    private static final String JSON_URL = "http://10.0.2.2/ElectronicStore/Api.php?apicall=login";

    EditText username;
    EditText password;
    Button loginBTN;
    String token;
    final String Token2 = "token1234";


    static final String CHANNEL_ID = "technopoints_id";
    static final String CHANNEL_NAME = "technopoints name";
    static final String CHANNEL_DESC = "technopoints desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        //Generating Token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token = task.getResult().getToken();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        username = (EditText) findViewById(R.id.ETusername);
        password = (EditText) findViewById(R.id.ETpassword);
        loginBTN = (Button) findViewById(R.id.loginBtn);


        //if user presses on login
        //calling the method login
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate username and password textViews
                if (username.getText().toString().trim().isEmpty()) {

                    username.setError("Username Filed should not be empty!");
                    username.requestFocus();
                }

                if (password.getText().toString().trim().isEmpty()) {

                    password.setError("password Filed should not be empty!");
                    password.requestFocus();
                } else {
                    LogInFunction();

                }

            }
        });


        //Creating notification channel for devices on and above Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


    }



    private void LogInFunction() {

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://10.0.2.2/Android/Usercheck.php?Name="+username.getText().toString()+"&&"+"Password="+password.getText().toString()+"&&"+"token="+token, null,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("user");

                            /*Toast.makeText(getApplicationContext(),"Data was sent To server :) ",Toast.LENGTH_SHORT).show();*/


                            String name =    jsonArray.getJSONObject(0).get("Name").toString();
                            String email =    jsonArray.getJSONObject(0).get("Email").toString();
                            String phone =    jsonArray.getJSONObject(0).get("Phone").toString();
                            String state =    jsonArray.getJSONObject(0).get("Stat").toString();

                            User user = new User(name,email,phone);

                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                            if(state.matches("1")){

                            Toast.makeText(getApplicationContext(),"Welcome "+name +" :)",Toast.LENGTH_SHORT).show();
                            /*Toast.makeText(getApplicationContext(),"Token: "+token,Toast.LENGTH_SHORT).show();*/


                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else if(state.matches("0")){
                                Toast.makeText(getApplicationContext(),"Incorrect Name or Password",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.println(Log.ERROR,"tag",error.getMessage());

                    }
                }




                ){

            //Sending POST Parameters: Email,Password
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();


                params.put("Name",username.getText().toString());
                params.put("Password",password.getText().toString());
                params.put("token",Token2);


                //returing the response
                return params;
            }



        };

// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }//End of LoginFunction();


}//End of login class



