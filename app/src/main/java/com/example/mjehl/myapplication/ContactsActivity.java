package com.example.mjehl.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.jar.Attributes;

public class ContactsActivity extends AppCompatActivity {

    private ListView contactsList;
    private ArrayAdapter<NameNumberContact> contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_text);

        final Context context = this;
        contactsList = (ListView) findViewById(R.id.contactsListView);
        ArrayList<NameNumberContact> contacts = getContacts(getApplicationContext().getContentResolver());
        contactsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(contactsAdapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                final NameNumberContact selectedFromList = (NameNumberContact) (contactsList.getItemAtPosition(myItemInt));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Title");
                alertDialogBuilder
                        .setMessage("Select this Contact?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                // Affirmative action -> pass intent and go to previous activity
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("contactName", selectedFromList.getName());
                                intent.putExtra("contactNumber", selectedFromList.getNumber());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public static ArrayList<NameNumberContact> getContacts(ContentResolver cr){
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<NameNumberContact> allContacts = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);

                    while (pCur.moveToNext())
                    {
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        allContacts.add(new NameNumberContact(contactName, contactNumber));
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }

        // Sort contacts
        Collections.sort(allContacts, new Comparator<NameNumberContact>() {
            @Override
            public int compare(NameNumberContact a, NameNumberContact b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });

        return allContacts;
    }
}
