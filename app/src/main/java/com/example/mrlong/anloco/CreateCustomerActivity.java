package com.example.mrlong.anloco;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Contains.Reference;

public class CreateCustomerActivity extends AppCompatActivity {
    EditText nameET,idET,addressET,phoneET,nameGroupET,idGroupET;
    Spinner priceGroupET,groupET;
    Button insertButton,insertGroupButton;
    List<String> priceCategories = new ArrayList<String>();
    ListView customerGroupListView;

    List<String> idGroup = new ArrayList<String>();
    List<String> nameGroup = new ArrayList<String>();

    ArrayAdapter<String> groupDataAdapter;
    ArrayAdapter groupCustomerListViewAdapter;
    final DatabaseReference groupCusRef = new Reference().getGroupCustomerRef();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        initView();

        observerCustomerGroup();

    }//onCreate()

    private void observerCustomerGroup() {
        idGroup.clear();
        nameGroup.clear();
        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                groupDataAdapter.notifyDataSetChanged();
                groupCustomerListViewAdapter.notifyDataSetChanged();
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


    //connect to activity element.
    private void initView() {
        priceCategories.add("price1");
        priceCategories.add("price2");
        priceCategories.add("price3");
        priceCategories.add("price4");
        priceCategories.add("present");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priceCategories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        // Creating adapter for spinner
        groupDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameGroup);

        // Drop down layout style - list view with radio button
        groupDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner




        nameET = (EditText)findViewById(R.id.nameCustomerEditText);
        groupET = (Spinner) findViewById(R.id.groupCustomerEditText);
        idET = (EditText)findViewById(R.id.idCustomerEditText);
        addressET = (EditText)findViewById(R.id.addressCustomerEditText);
        phoneET = (EditText)findViewById(R.id.phoneCustomerEditText);
        priceGroupET = (Spinner) findViewById(R.id.priceGroupSpinner);


        priceGroupET.setAdapter(dataAdapter);
        groupET.setAdapter(groupDataAdapter);

        insertButton = (Button)findViewById(R.id.insertNewCustomerButton);

        nameGroupET = (EditText)findViewById(R.id.nameGroupCustomerEditText);
        idGroupET = (EditText)findViewById(R.id.idGroupCustomerEditText);
        insertGroupButton = (Button)findViewById(R.id.insertNewGroupCustomerButton);

        customerGroupListView = (ListView)findViewById(R.id.groupCustomerListView);
        groupCustomerListViewAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameGroup);
        customerGroupListView.setAdapter(groupCustomerListViewAdapter);

        //set listener listview
        customerGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameGroupET.setText(nameGroup.get(position));
                idGroupET.setText(idGroup.get(position));
            }
        });

//        customerGroupListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                groupCusRef.child(idGroup.get(position)).removeValue();
//                nameGroupET.setText("");
//                idGroupET.setText("");
//                observerCustomerGroup();
//                Toast.makeText(CreateCustomerActivity.this,"Đã xoá",Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//        });
        //set listener add group
        insertGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameGroupET.getText().toString();
                String id = idGroupET.getText().toString();
                groupCusRef.child(id).setValue(name);
                nameGroupET.setText("");
                idGroupET.setText("");
                observerCustomerGroup();

            }
        });

    }


}
