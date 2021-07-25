package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;


import java.util.List;

import com.example.myapplication.Adapters.Allcontacts;
import com.example.myapplication.Databases.*;
public class AddPeople extends AppCompatActivity {
    public ListView listView;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        listView =(ListView) findViewById(R.id.peoples);
        progressBar = findViewById(R.id.progress);
        DatabasesCreationWithTable d_c_w_t = new DatabasesCreationWithTable(this);
        List<All_Contact_Users> contacts = d_c_w_t.fetch_all_contact();
        Log.e("size",contacts.size()+"");
        for(int i=0;i<contacts.size();i++){
            Log.e("name",contacts.get(i).name);
            Log.e("number",contacts.get(i).number);
        }

        new MyTask().execute(contacts);
        
    }

    public class MyTask extends AsyncTask<List<All_Contact_Users>,Void, String>{
        public String[] name;
        public String[] number;
        public Allcontacts allcontacts;

        @Override
        protected void onPostExecute(String s) {
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            listView.setAdapter(allcontacts);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                // if selected user in in our database


                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("name", name[position]);
                bundle.putString("number", number[position]);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                // the user is not in database please invit him/she

            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(List<All_Contact_Users>... lists) {
            name = new String[lists[0].size()];
            number = new String[lists[0].size()];
            List<All_Contact_Users> contacts = lists[0];


            for(All_Contact_Users contact:contacts){
                name[contacts.indexOf(contact)] = contact.getName();
                number[contacts.indexOf(contact)] = contact.getNumber();
            }

            allcontacts = new Allcontacts(AddPeople.this,name,number);

            return "Task Completed";
        }
    }



}