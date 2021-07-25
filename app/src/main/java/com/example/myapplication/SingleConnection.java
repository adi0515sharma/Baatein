package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapters.FirebaseUsersForMessage;


import com.example.myapplication.Databases.App_users;
import com.example.myapplication.Databases.DatabasesCreationWithTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleConnection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleConnection extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;
    public FloatingActionButton add_people;
    public SingleConnection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleConnection.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleConnection newInstance(String param1, String param2) {
        SingleConnection fragment = new SingleConnection();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    void updateUi(){

    }

    void updateFirebase(String number){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query check_user_in_db = databaseReference.orderByKey().endAt(number);
        check_user_in_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0){
                    Toast.makeText(getActivity(),"Sorry User Not Exist In This App",Toast.LENGTH_LONG).show();
                }
                else{

                    updateDatabase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Value","value not find bro");
            }
        });
//        databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
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
//
//                    //Log.e("data wait",people_have_app.size()+"");
//                    Log.e("user",number);
//                    Log.e("token",snapshot.child("token").getValue().toString());
////                    snapshot.child("token").getValue().toString();
////                    snapshot.getRef().child("message_sent").setValue()
//
////                    User user1 = new User(.getText().toString(), password.getText().toString(), token, phone.getText().toString(), name.getText().toString());
////
////                    database.getReference().child("Users").child("Specific").child(number).setValue();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
    }
    void updateDatabase(App_users app_users){
        DatabasesCreationWithTable pe_db = new DatabasesCreationWithTable(getActivity());
        pe_db.write_new_user(app_users);
    }
    void create_personal_db(String name , String number){

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999){
            Bundle extras = data.getExtras();
            String name = extras.getString("name");
            String number = extras.getString("number");
            Log.e("-----------","-----------------------------------");
            Log.e("name",name);
            Log.e("number",number);
            // 4 task we have to do
            // 1. display it one scrren
            // 2. update firebase
            updateFirebase(number);
            // 3. update mysqlite

            // 4. create it self table in database
            //create_personal_db(number);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_single, container, false);
        add_people = view.findViewById(R.id.add_single_person);
        add_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIintent=new Intent(getActivity(), AddPeople.class);
                startActivityForResult(myIintent, 999);
            }
        });
        return view;

    }
}