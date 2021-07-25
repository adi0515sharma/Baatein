package com.example.myapplication.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;




import java.util.ArrayList;
import java.util.List;

public class DatabasesCreationWithTable extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "IN_Chat";
    public static int DATABASE_VERSION = 1;

    public String All_Contact_Table_Name = "PhoneContacts";
    public String PhoneContacts_Field_One = "name";
    public String PhoneContacts_Field_Two = "number";

    public String All_AppUsers_Table_Name = "AppUsers";
    public String AppUsers_Field_One = "name";
    public String AppUsers_Field_Two = "number";

    public String Specific_Field_One = "Data";
    public String Specific_Field_Two = "Time";
    public String Specific_Field_Three = "From";
    public String Specific_Field_Four = "To";
    public String Specific_Field_Five = "Message";
    public String Specific_Field_Six = "Seen";



    public DatabasesCreationWithTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // This is For Phone Contacs

    public void create_phone_contact_table(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "CREATE TABLE IF NOT EXISTS " + All_Contact_Table_Name + "("
                + AppUsers_Field_One + " BLOB ," + AppUsers_Field_Two + " TEXT" +")";
        db.execSQL(Query);
    }

    public boolean write_contact(All_Contact_Users all_contact_users){
        SQLiteDatabase db = this.getWritableDatabase();


        String countQuery = "SELECT  * FROM " + All_Contact_Table_Name;
        SQLiteDatabase count = this.getReadableDatabase();
        Cursor cursor = count.rawQuery(countQuery, null);
        int count1 = cursor.getCount();
        cursor.close();
        if(count1>1){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(PhoneContacts_Field_One, all_contact_users.name);
        values.put(PhoneContacts_Field_Two, all_contact_users.number);
        db.insert(All_Contact_Table_Name, null, values);
        db.close();

        return true;
    }

    public List<All_Contact_Users> fetch_all_contact(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<All_Contact_Users> all_contact_users = new ArrayList<>();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + All_Contact_Table_Name, null);
        if (cursorCourses.moveToFirst()) {
            do {

                All_Contact_Users all_contact_users1 = new All_Contact_Users();
                all_contact_users1.name = cursorCourses.getString(0);
                // on below line we are adding the data from cursor to our array list.
                all_contact_users1.number = cursorCourses.getString(1);
                all_contact_users.add(all_contact_users1);

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        return all_contact_users;
        // on below line we are creating a new array list.
    }

    // This is For App_User

    public void create_all_app_user(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "CREATE TABLE IF NOT EXISTS " + All_Contact_Table_Name + "("
                + PhoneContacts_Field_One + " BLOB ," + PhoneContacts_Field_Two + " TEXT" +")";
        db.execSQL(Query);
    }

    public boolean write_new_user(App_users app_users){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppUsers_Field_One, app_users.name);
        values.put(AppUsers_Field_Two, app_users.number);
        db.insert(All_AppUsers_Table_Name, null, values);
        db.close();

        return true;
    }


    public List<App_users> fetch_all_app_user(){

        SQLiteDatabase db = this.getReadableDatabase();
        List<App_users> all_app_users = new ArrayList<>();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + All_AppUsers_Table_Name, null);
        if (cursorCourses.moveToFirst()) {
            do {

                App_users app_users = new App_users();
                app_users.name = cursorCourses.getString(0);
                // on below line we are adding the data from cursor to our array list.
                app_users.number = cursorCourses.getString(1);
                all_app_users.add(app_users);

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        return all_app_users;
        // on below line we are creating a new array list.
    }


    // This is For Specific App_User

    public void create_specific_app_users(String TableName){
        SQLiteDatabase db = this.getWritableDatabase();

        String Query = "CREATE TABLE IF NOT EXISTS " + TableName+ "("
                + PhoneContacts_Field_One + " BLOB ," + PhoneContacts_Field_Two + " TEXT" +")";
        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
