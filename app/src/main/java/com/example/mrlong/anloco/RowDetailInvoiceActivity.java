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

import java.util.List;

import Objects.ProductToCart;

public class RowDetailInvoiceActivity extends ArrayAdapter<ProductToCart> {


    public RowDetailInvoiceActivity(@NonNull Context context, int resource, @NonNull List<ProductToCart> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_detail_invoice,null);

        }
        ProductToCart ptc = getItem(position);

        if(ptc != null){
            TextView idPrd = v.findViewById(R.id.idPrdOfInlTV);
                idPrd.setText(ptc.idProduct);
            TextView namePrd = v.findViewById(R.id.namePrdOfInlTV);
                namePrd.setText(ptc.nameProduct);
            TextView quantum = v.findViewById(R.id.quantumOfPrdTV);
                quantum.setText(ptc.quantum);
            TextView price = v.findViewById(R.id.priceOfPrdTV);
                price.setText(ptc.price);
            TextView total = v.findViewById(R.id.totalOfPrdTV);
                total.setText(ptc.total);
        }

        return v;
    }
}
