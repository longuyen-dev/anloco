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

import Objects.ProductToCart;

public class RowProductCartActivity extends ArrayAdapter<ProductToCart> {

    public RowProductCartActivity(@NonNull Context context, int resource, @NonNull ArrayList<ProductToCart> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v==null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_product_cart, null);

        }
        ProductToCart prdC = getItem(position);

        if(prdC != null){
            TextView idPrdC = v.findViewById(R.id.idPrdCartTV);
                idPrdC.setText(prdC.idProduct);
            TextView namePrdC = v.findViewById(R.id.namePrdCartTV);
                namePrdC.setText(prdC.nameProduct);
            TextView quantumPrdC = v.findViewById(R.id.quantumCartTV);
                quantumPrdC.setText(prdC.quantum);
            TextView pricePrdC = v.findViewById(R.id.pricePrdCartTV);
                pricePrdC.setText(prdC.price);
            TextView totalPrdC = v.findViewById(R.id.totalPrdCartTV);
                totalPrdC.setText(prdC.total);
        }

        return v;
    }
}
