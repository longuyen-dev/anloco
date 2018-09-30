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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Objects.Owe;

public class RowOweActivity extends ArrayAdapter<Owe> {


    public RowOweActivity(@NonNull Context context, int resource, @NonNull List<Owe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_owe,null);

        }
        Owe o = getItem(position);

        if(o != null){
            TextView customerTV = v.findViewById(R.id.idCustomerTextView);
                customerTV.setText(o.idCustomer);
            TextView oweTV = v.findViewById(R.id.oweCustomerTextView);
                String oweString = String.format("%,d",o.owe);
                oweTV.setText(oweString);
        }
        return v;
    }
}
