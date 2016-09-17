package com.example.mjehl.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsTest extends AppCompatActivity {

    private ListView contactsList;
    private ArrayAdapter<String> contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_text);

        contactsList = (ListView) findViewById(R.id.contactsListView);
        ArrayList<String> contacts = getContacts();
        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(contactsAdapter);
    }

    private ArrayList<String> getContacts(){
        ContentResolver cr = getApplicationContext().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<String> allContacts = new ArrayList<String>();

        if (cursor.moveToFirst())
        {
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        allContacts.add(contactNumber);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }

        return allContacts;
    }
}
