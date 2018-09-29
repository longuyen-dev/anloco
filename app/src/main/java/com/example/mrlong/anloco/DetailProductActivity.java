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

import Contains.Reference;
import Objects.Product;

public class DetailProductActivity extends AppCompatActivity {

    EditText namePrdET,idPrdET,p1ET,p2ET,p3ET,p4ET;
    Spinner groupPrdSP;
    Button editDetailPrdBT,deletePrdBT;

    ArrayList<String> idGroup = new ArrayList<String>();
    ArrayList<String> nameGroup = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();

        Intent i = getIntent();
        Product setlectedPrd = (Product) i.getSerializableExtra("prd");
        namePrdET.setText(setlectedPrd.nameProduct);
        idPrdET.setText(setlectedPrd.idProduct);
        p1ET.setText(setlectedPrd.price1);
        p2ET.setText(setlectedPrd.price2);
        p3ET.setText(setlectedPrd.price3);
        p4ET.setText(setlectedPrd.price4);
        String group = setlectedPrd.groupProduct;

        setupGroupSpinner(group);
    }

    private void setupGroupSpinner(final String key) {
        final DatabaseReference groupPrdRef = new Reference().getGroupProductRef();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nameGroup);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupPrdSP.setAdapter(arrayAdapter);

        groupPrdRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                arrayAdapter.notifyDataSetChanged();
                int p = idGroup.indexOf(key);
                groupPrdSP.setSelection(p);
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
        namePrdET = findViewById(R.id.detailNamePrdtomerET);
        idPrdET = findViewById(R.id.detailIdPrdET);
        p1ET = findViewById(R.id.price1EditText);
        p2ET = findViewById(R.id.price2EditText);
        p3ET = findViewById(R.id.price3EditText);
        p4ET = findViewById(R.id.price4EditText);
        groupPrdSP = findViewById(R.id.detailGroupPrdSP);

    }
}
