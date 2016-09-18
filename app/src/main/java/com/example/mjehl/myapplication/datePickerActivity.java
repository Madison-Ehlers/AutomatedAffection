package com.example.mjehl.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class datePickerActivity extends AppCompatActivity {

    Button btn;
    int year_x,month_x,day_x;
    static final int DILOG_ID = 0;
    static String birthdayDate = "Birthday";
    static String anniversaryDate = "Anniversary";
    static String specialEventDate = "Special Event 1";
    static boolean birthday = false;
    static boolean anniversary = false;
    static boolean specialEvent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        String[] menu = {birthdayDate, anniversaryDate, specialEventDate};
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    birthday = true;
                    anniversary = false;
                    specialEvent = false;
                }
                else if(position == 1) {
                    birthday = false;
                    anniversary = true;
                    specialEvent = false;
                }
                else if(position == 2) {
                    birthday = false;
                    anniversary = false;
                    specialEvent = true;
                }
                showDialog(DILOG_ID);
            }
        });
    }

    public void showDialogOnButtonClick() {
        btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendNotification(v);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DILOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            //Toast.makeText(datePickerActivity.this, month_x + "/" + day_x + "/" + year_x, Toast.LENGTH_LONG).show();
            String date = month_x + "/" + day_x + "/" + year_x;

            if(birthday == true) {
                birthdayDate = "Birthday: " + date;
            }
            else if(anniversary == true) {
                anniversaryDate = "Anniversary: " + date;
            }
            else if(specialEvent == true) {
                specialEventDate = "Special Event 1: " + date;
            }

        }
    };

    private void sendNotification(View view) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Update Successful")
                .setContentText("Your mom's events have been updated");

        Intent resultIntent = new Intent(this, getClass());
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder.setAutoCancel(true);
        mNotifyMgr.notify(001, mBuilder.build());

        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
    }

}
