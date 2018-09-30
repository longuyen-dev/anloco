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

import Objects.Invoice;

public class RowDetailOweActivity extends ArrayAdapter<Invoice> {


    public RowDetailOweActivity(@NonNull Context context, int resource, @NonNull List<Invoice> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_detail_owe,null);
        }
        Invoice ivl = getItem(position);

        if(ivl != null){
            TextView idIvl = v.findViewById(R.id.idInvoiceTextView);
                idIvl.setText(ivl.idInvoice);
            TextView dateIvl = v.findViewById(R.id.dateInvoiceTextView);
                dateIvl.setText(ivl.dateInvoice);
            TextView totalIvl = v.findViewById(R.id.totalTextView);
                totalIvl.setText(ivl.total);
            TextView payIvl = v.findViewById(R.id.payTextView);
                payIvl.setText(ivl.pay);
            TextView ownIvl = v.findViewById(R.id.oweTextView);
                ownIvl.setText(ivl.own);
        }

        return v;
    }
}
