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

import Contains.Lib;
import Objects.Invoice;

public class RowInvoiceActivity extends ArrayAdapter<Invoice> {


    public RowInvoiceActivity(@NonNull Context context, int resource, @NonNull List<Invoice> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_invoice,null);
        }
        Invoice ivl = getItem(position);

        if(ivl != null){
            Lib lib = new Lib();
            TextView idIvl = v.findViewById(R.id.idInvoiceTV);
                idIvl.setText(ivl.idInvoice);
            TextView dateIvl = v.findViewById(R.id.dateInvoiceTV);
                dateIvl.setText(ivl.dateInvoice);
            TextView nameCus = v.findViewById(R.id.nameCustomerIvlTV);
                nameCus.setText(ivl.customer);
            TextView totalIvl = v.findViewById(R.id.totalIvlTV);
                totalIvl.setText(lib.addDotToString(ivl.total));
            TextView payIvl = v.findViewById(R.id.payIvlTV);

                payIvl.setText(lib.addDotToString(ivl.pay));
            TextView ownIvl = v.findViewById(R.id.ownIvlTV);
                ownIvl.setText(lib.addDotToString(ivl.own));
        }

        return v;
    }
}
