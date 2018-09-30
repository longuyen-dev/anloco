package com.example.mrlong.anloco;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import Contains.Reference;
import Objects.Invoice;

public class DetailOweActivity extends AppCompatActivity {

    TextView idCustomerTV, nameCustomerTV;
    Button payOweButton;
    ListView detailOweListView;

    RowDetailOweActivity detailOwnListViewAdapter;
    ArrayList<Invoice> listOwe = new ArrayList<>();
    String idCustomerFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_owe);

        //get intent of ID customer
        Intent i = getIntent();
        idCustomerFromIntent = i.getStringExtra("nameCus");

        initView();
        setupListView();
        observerOwe();
    }

    private void observerOwe() {
        DatabaseReference invoiceRef = new Reference().getInvoiceRef();
        invoiceRef.orderByChild("customer").equalTo(idCustomerFromIntent).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Invoice ivl = dataSnapshot.getValue(Invoice.class);
                ivl.pay = ivl.own;
                if(listOwe.size() != 0){
                    int oldOwe = Integer.parseInt(listOwe.get(listOwe.size() - 1 ).own);
                    int newOwe = oldOwe + Integer.parseInt(ivl.own);
                    ivl.own = String.valueOf(newOwe);
                }



                listOwe.add(ivl);
                detailOwnListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });






    }

    private void setupListView() {
        detailOweListView = findViewById(R.id.detailOweListView);
        detailOwnListViewAdapter = new RowDetailOweActivity(this,R.layout.activity_row_detail_owe,listOwe);
        detailOweListView.setAdapter(detailOwnListViewAdapter);
    }

    private void initView() {
        idCustomerTV = findViewById(R.id.idCustomerTextView);
        nameCustomerTV = findViewById(R.id.nameCustomerTextView);
        payOweButton = findViewById(R.id.payOweButton);
        payOweButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Invoice i:listOwe){
                    System.out.println(i.customer+" | "+i.own);
                }
            }
        });

        idCustomerTV.setText(idCustomerFromIntent);
    }
}
