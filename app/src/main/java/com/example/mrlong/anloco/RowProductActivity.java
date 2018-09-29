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

import com.example.mrlong.anloco.R;

import java.util.List;

import Objects.Customer;
import Objects.Product;

public class RowProductActivity extends ArrayAdapter<Product> {


    public RowProductActivity(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v== null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_product,null);

        }
        Product prd = getItem(position);

        if (prd != null){
            TextView idPrd = v.findViewById(R.id.idProductTextView);
                idPrd.setText(prd.idProduct);
            TextView namePrd = v.findViewById(R.id.nameProductTextView);
                namePrd.setText(prd.nameProduct);
            TextView grPrd = v.findViewById(R.id.groupProductTextView);
                grPrd.setText(prd.groupProduct);
            TextView p1 = v.findViewById(R.id.price1TextView);
                p1.setText(prd.price1);
            TextView p2 = v.findViewById(R.id.price2TextView);
                p2.setText(prd.price2);
            TextView p3 = v.findViewById(R.id.price3TextView);
                p3.setText(prd.price3);
            TextView p4 = v.findViewById(R.id.price4TextView);
                p4.setText(prd.price4);
        }
        return v;
    }
}
