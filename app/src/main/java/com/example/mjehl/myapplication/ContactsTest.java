package com.example.mjehl.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.jar.Attributes;

public class ContactsTest extends AppCompatActivity {

    private ListView contactsList;
    private ArrayAdapter<NameNumberContact> contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_text);

        contactsList = (ListView) findViewById(R.id.contactsListView);
        ArrayList<NameNumberContact> contacts = getContacts();
        contactsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactsList.setAdapter(contactsAdapter);
    }

    private ArrayList<NameNumberContact> getContacts(){
        ContentResolver cr = getApplicationContext().getContentResolver(); //Activity/Application android.content.Context
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
