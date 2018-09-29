package com.example.mrlong.anloco;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Contains.Reference;
import Objects.Invoice;

public class OweActivity extends Fragment {

    View mRootView;
    AutoCompleteTextView searchCustomerAT;
    Button filterButton;
    ListView oweListView;


    DatabaseReference invoiceRef = new Reference().getInvoiceRef();


    Map<String, Integer> oweList = new HashMap<>();
    RowOweActivity adapter = new RowOweActivity(oweList);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_owe, container, false);

        initView();
        setupListView();
        observerAllOwe();


        return mRootView;
    }
    public void setupListView() {
        oweListView = mRootView.findViewById(R.id.oweListView);
        oweListView.setAdapter(adapter);
    }


    private void observerAllOwe() {
        oweList.clear();
        invoiceRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Invoice ivl = dataSnapshot.getValue(Invoice.class);
                int own = Integer.parseInt(ivl.own);
                if(!oweList.containsKey(ivl.customer)){

                    oweList.put(ivl.customer,own);
                }else{

                    int newOwe = oweList.get(ivl.customer) + own;
                    oweList.put(ivl.customer,newOwe);

                }
                adapter.notifyDataSetChanged();
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

    private void initView() {
        searchCustomerAT = mRootView.findViewById(R.id.searchCustomerAutoComplete);
        filterButton = mRootView.findViewById(R.id.filterCustomerButton);


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(oweList);
            }
        });
    }
}
