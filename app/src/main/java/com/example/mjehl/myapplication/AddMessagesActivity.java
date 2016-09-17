package com.example.mjehl.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AddMessagesActivity extends AppCompatActivity {

    Button storeMessageBtn;
    EditText textInput;
    AddMessagesActivity that;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_messages);

        textInput =  (EditText) findViewById(R.id.messageInputText);
        storeMessageBtn = (Button) findViewById(R.id.store_message_db);
        that = this;
        storeMessageBtn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        try{
                            final TextView mTextView = (TextView) findViewById(R.id.text);
// Instantiate the RequestQueue.
                            RequestQueue queue = Volley.newRequestQueue(that);
                            String url ="http://hackisu.madisonehlers.com/send_request.php";



                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Display the first 500 characters of the response string.
                                            Toast.makeText(getApplicationContext(), "Successfully added "+ textInput.getText().toString() + " to the list of messages that user will use", Toast.LENGTH_LONG).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "That didn't work", Toast.LENGTH_LONG).show();
                                }
                            }){

// Add the request to the RequestQueue.
                            @Override
                            protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("data", textInput.getText().toString());

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                            queue.add(stringRequest);
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Failed to Connect", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
