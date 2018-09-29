package com.example.mrlong.anloco;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contains.Reference;
import Objects.Customer;

public class DetailCustomerActivity extends AppCompatActivity {

    EditText nameCusET, idCusET, addressCusET, phoneCusET;
    Spinner groupCusSP, priceSP;
    Button supplierBT, editDetailCusBT, deleteCusET;

    List<String> priceCategories = new ArrayList<String>();
    ArrayList<String> idGroup = new ArrayList<String>();
    ArrayList<String> nameGroup = new ArrayList<String>();

    ArrayAdapter<String> groupDataAdapter;
    final DatabaseReference groupCusRef = new Reference().getGroupCustomerRef();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);


        initView();


        Intent i = getIntent();
        Customer selectedCus = (Customer) i.getSerializableExtra("cus");
        String grTerm = selectedCus.groupCustomer;
        nameCusET.setText(selectedCus.nameCustomer);
        idCusET.setText(selectedCus.idCustomer);
        addressCusET.setText(selectedCus.addressCustomer);
        phoneCusET.setText(selectedCus.phoneCustomer);
        String prTerm = selectedCus.priceGroup;
//        groupCusSP.setSelection(positionGroup);

        setupGroupSpinner(grTerm);
        setupPriceSpinner(prTerm);

    }

    private void setupGroupSpinner(final String key) {
        final DatabaseReference groupCusRef = new Reference().getGroupCustomerRef();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nameGroup);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupCusSP.setAdapter(arrayAdapter);

        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                arrayAdapter.notifyDataSetChanged();
                int p = idGroup.indexOf(key);
                groupCusSP.setSelection(p);
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

    private void setupPriceSpinner(String key) {
        priceCategories.add("price1");
        priceCategories.add("price2");
        priceCategories.add("price3");
        priceCategories.add("price4");

        int p = priceCategories.indexOf(key);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priceCategories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priceSP.setAdapter(dataAdapter);

        priceSP.setSelection(p);

    }

    private void initView() {
        nameCusET = (EditText)findViewById(R.id.detailNameCustomerET);
        idCusET = (EditText)findViewById(R.id.detailIdCusET);
        addressCusET = (EditText)findViewById(R.id.detailAddressCustomerET);
        phoneCusET = (EditText)findViewById(R.id.detailPhoneCusET);

        groupCusSP = (Spinner)findViewById(R.id.detailGroupCusSP);
        priceSP = (Spinner)findViewById(R.id.detailPriceSP);




    }
    private void observerCustomerGroup() {
        idGroup.clear();
        nameGroup.clear();
        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                groupDataAdapter.notifyDataSetChanged();
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
}
