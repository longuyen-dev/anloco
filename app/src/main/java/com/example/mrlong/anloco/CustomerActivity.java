package com.example.mrlong.anloco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomerListViewAdapter;
import Contains.Reference;
import Objects.Customer;

public class CustomerActivity extends Fragment {

    private View mRootView;
    ListView customerListView;
    Spinner groupCustomerSpinnner;

    List<String> idGroup = new ArrayList<>();
    List<String> nameGroup = new ArrayList<>();

    ArrayList<Customer> customerToListView = new ArrayList<>();
    ArrayList<Customer> allCustomers = new ArrayList<>();

    CustomerListViewAdapter customerListViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_customer,container,false);


        initView();
        setupGroupSpinner();
        setupCustomerListView();
        readAllCustomer();


        return mRootView;
    }

    private void setupCustomerListView() {
        customerListView = mRootView.findViewById(R.id.customerListView);
        customerListViewAdapter = new CustomerListViewAdapter(mRootView.getContext(),R.layout.activity_row_customer, customerToListView);
        customerListView.setAdapter(customerListViewAdapter);
        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCus = customerToListView.get(position);
                Intent gotoDetail = new Intent(mRootView.getContext(),DetailCustomerActivity.class);

                gotoDetail.putExtra("cus",selectedCus);
                startActivity(gotoDetail);
            }
        });
    }

    private void readAllCustomer() {
        customerToListView.clear();
        allCustomers.clear();
        DatabaseReference customerRef = new Reference().getCustomerRef();
        customerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Customer cus = dataSnapshot.getValue(Customer.class);
                allCustomers.add(cus);
                customerToListView.add(cus);
                customerListViewAdapter.notifyDataSetChanged();
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
        });//read all customer
    }

    private void setupGroupSpinner() {
        idGroup.clear();
        nameGroup.clear();
        groupCustomerSpinnner = mRootView.findViewById(R.id.groupCustomerSpinner);
        final ArrayAdapter<String> dataSpinnerAdapter = new ArrayAdapter<>(mRootView.getContext(), android.R.layout.simple_spinner_item, nameGroup);
        nameGroup.add("Tất cả");
        idGroup.add("");
        final DatabaseReference groupCusRef = new Reference().getGroupCustomerRef();
        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                dataSpinnerAdapter.notifyDataSetChanged();
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
        });//observer group
        dataSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupCustomerSpinnner.setAdapter(dataSpinnerAdapter);
        groupCustomerSpinnner.setSelection(0,false);
        groupCustomerSpinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerToListView.clear();
                if(position == 0){

                    for(Customer d: allCustomers){
                        customerToListView.add(d);
                        customerListViewAdapter.notifyDataSetChanged();
                    }

                }else {
                    for(Customer d: allCustomers){
                        if(d.groupCustomer.equals(idGroup.get(position))){

                            customerToListView.add(d);
                            customerListViewAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }//setup group spinner

    private void initView() {
        Button addCustomerButton = mRootView.findViewById(R.id.gotoAddCusButton);
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCreateCus = new Intent(mRootView.getContext(), CreateCustomerActivity.class);
                startActivity(gotoCreateCus);
            }
        });

        final EditText searchCusET = mRootView.findViewById(R.id.searchCustomerEditText);
        searchCusET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = searchCusET.getText().toString();
                searCus(key);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

    private void searCus(String key) {
        customerToListView.clear();
        for (Customer cus : allCustomers){
            if (cus.nameCustomer.contains(key) || cus.idCustomer.contains(key)){
                if(!customerToListView.contains(cus)){
                    customerToListView.add(cus);
                }
                customerListViewAdapter.notifyDataSetChanged();
            }
        }

    }
}
