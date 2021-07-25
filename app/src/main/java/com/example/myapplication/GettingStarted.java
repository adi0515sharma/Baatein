package com.example.myapplication;


import com.example.myapplication.Databases.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GettingStarted extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
        new CheckToDatabase().execute();
    }




    private class CheckToDatabase extends AsyncTask<Void,Integer, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabasesCreationWithTable db = new DatabasesCreationWithTable(GettingStarted.this);
            db.create_phone_contact_table();

            List<All_Contact_Users> all_contact_users = FetchContactFromMobile();

            for(All_Contact_Users contacts1:all_contact_users) {
                if(!db.write_contact(contacts1)){
                    break;
                }
            }

            db.create_all_app_user();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CallWorkDone();
        }

        public void CallWorkDone(){
            Intent i = new Intent(GettingStarted.this,HomeScreen.class);
            startActivity(i);
        }

//        public void FetchDatabaseUser(List<Person> key){
//
////            database = FirebaseDatabase.getInstance();
////            databaseReference = database.getReference();
////
////            ref = FirebaseDatabase.getInstance().getReference().child("Users");
////            ref.addValueEventListener(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    List<String> number = new ArrayList<>();
////                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
////                        number.add(dataSnapshot.getKey());
////                    }
////
////                    for(Person person: key){
////                        if(number.contains(person.myNumber)){
////                            people_have_app.add(person);
////                        }
////                    }
////
////                    Log.e("data wait",people_have_app.size()+"");
////
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////
////                }
////            });
//        }
//
//
        public List<All_Contact_Users> FetchContactFromMobile() {
            ArrayList<All_Contact_Users> contactList = new ArrayList<All_Contact_Users>();

            Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
            String[] PROJECTION = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
            };
            String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
            Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, SELECTION, null, null);


            if (contacts.getCount() > 0) {
                while (contacts.moveToNext()) {
                    All_Contact_Users aContact = new All_Contact_Users();
                    int idFieldColumnIndex = 0;
                    int nameFieldColumnIndex = 0;
                    int numberFieldColumnIndex = 0;

                    String contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));

                    nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    if (nameFieldColumnIndex > -1) {
                        aContact.setName(contacts.getString(nameFieldColumnIndex));
                    }

                    PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    final Cursor phone = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contactId)}, null);
                    if (phone.moveToFirst()) {
                        while (!phone.isAfterLast()) {
                            numberFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            if (numberFieldColumnIndex > -1) {
                                aContact.setNumber(phone.getString(numberFieldColumnIndex));
                                phone.moveToNext();
                                TelephonyManager mTelephonyMgr;
                                mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                if (ActivityCompat.checkSelfPermission(GettingStarted.this,
                                        Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GettingStarted.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GettingStarted.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    Log.e("contact","permission not granted");
                                    return null;
                                }
                                if (!mTelephonyMgr.getLine1Number().contains(aContact.getNumber())) {
                                    contactList.add(aContact);
                                }
                            }
                        }
                    }
                    phone.close();
                }

                contacts.close();
            }

            return contactList;
        }

    }
}