package com.example.asus.testpermossion;

import android.Manifest;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {


    final private int REQUEST_READ_CONTACTS = 123;
    final public int REQUEST_CAMERA_PERMISSION = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //  ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, 123);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 123);


        }


        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            Log.d("tag2 ", "in camera permission ");
        }






    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                Log.d("tag3 ", "in case " + requestCode);

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "REQUEST_READ_CONTACTS IS GRANTED", Toast.LENGTH_LONG).show();
                    ListContacts();

                } else {
                    Toast.makeText(this, "REQUEST_READ_CONTACTS DENIED", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
            }


            return;
        }


    }



    protected void ListContacts() {
        Uri allContacts = Uri.parse("content://contacts/people");
        Cursor c;
        CursorLoader cursorLoader = new CursorLoader(this, allContacts, null, null, null, null);
        c = cursorLoader.loadInBackground();
        String[] columns = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID};
        int[] views = new int[]{R.id.contactName, R.id.contactID};
        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(this, R.layout.activity_main, c, columns, views, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        this.setListAdapter(adapter);


    }
}