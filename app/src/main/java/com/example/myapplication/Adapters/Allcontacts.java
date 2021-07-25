package com.example.myapplication.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Allcontacts extends ArrayAdapter<String> {

    public Activity context;
    public String[] name;
    public String[] number;

    public Allcontacts(Activity context, String[] name, String[] number) {
        super(context, R.layout.user, name);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.name=name;
        this.number=number;

    }



    public View getView(int position, View view, ViewGroup parent) {
        
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.user, null,true);

        TextView name1 = (TextView) rowView.findViewById(R.id.user_name);
        TextView number1 = (TextView) rowView.findViewById(R.id.number);

        name1.setText(this.name[position]);
        number1.setText(this.number[position]);


        return rowView;

    };
}
