package com.example.mrlong.anloco;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RowAccountActivity extends ArrayAdapter<userTmp> {


    public RowAccountActivity(@NonNull Context context, int resource, @NonNull List<userTmp> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_account,null);

        }

        userTmp userTmp = getItem(position);

        TextView uTV = v.findViewById(R.id.usernameTextView);
            uTV.setText(userTmp.username);
        TextView rTV = v.findViewById(R.id.ruleTextView);
            rTV.setText(userTmp.rule);

        return  v;
    }
}
