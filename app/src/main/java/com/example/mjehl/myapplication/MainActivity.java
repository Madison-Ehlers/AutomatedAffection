package com.example.mjehl.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    String txtPhoneNO;
    String txtMessage;
    EditText messageToSend;
    Button btnSend;
    private Map<String, String> params;
    private Response.Listener<JSONObject> listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://hackisu.madisonehlers.com/messages";
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        JSONArray myJSON;
                        //JSONOBject j = new JSONObject(response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        try{
                            myJSON = new JSONArray(response.toString());
                            Toast.makeText(getApplicationContext(),"Successfully parsed", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            System.out.println(e);
                            myJSON = new JSONArray();
                            Toast.makeText(getApplicationContext(),"Error parsing Json", Toast.LENGTH_SHORT).show();
                        }
                        for(int n = 0; n < myJSON.length(); n++)
                        {
                            try{
                                JSONObject object = myJSON.getJSONObject(n);

                                Iterator<String> x = object.keys();
                                while(x.hasNext()){
                                    String key = x.next();
                                    System.out.println(key);
                                    String value = (String) object.get(key);
                                    System.out.println(value);
                                    Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch(Exception e){

                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                return params;
            };
        };

        queue.add(strRequest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtPhoneNO = "2245956550";
        txtMessage = "Hey";
    }

    public void addMessages(MenuItem item){
        Intent intent = new Intent(this, AddMessagesActivity.class);
        startActivity(intent);
    }

    public void chooseContact(View v){
        Intent intent = new Intent(this, ContactsTest.class);
        startActivity(intent);
    }

    public void goToSettings(MenuItem item){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }
    public void sendMessage(View v){
        messageToSend = (EditText) findViewById(R.id.text_to_send);
        Toast.makeText(getApplicationContext(), "SMS Sent. : " + messageToSend.getText().toString() , Toast.LENGTH_LONG).show();
        //sendText("2245956550", messageToSend.getText().toString());
        sendText("7122993195", messageToSend.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void sendText(String phoneNo, String message){
        SmsManager sms = SmsManager.getDefault();
        try{
            sms.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent. ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS FAIL. Please try again!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
