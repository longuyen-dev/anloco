package com.example.mrlong.anloco;

import android.content.Intent;
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

import Contains.Reference;
import Objects.Product;

public class ProductActivity extends Fragment {

    private View mRootView;
    Spinner groupProductSpinner;
    ListView productListView;


    List<String> idGroup = new ArrayList<>();
    List<String> nameGroup = new ArrayList<>();
    ArrayList<Product> allProduct = new ArrayList<>();
    ArrayList<Product> productToListView = new ArrayList<>();

    RowProductActivity productListViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_product,container,false);
        initView();

        setupSpinner();
        setupListView();
        readAllProduct();
        return mRootView;
    }

    private void readAllProduct() {
        productToListView.clear();
        DatabaseReference productRef = new Reference().getProductRef();
        productRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product = dataSnapshot.getValue(Product.class);
                allProduct.add(product);
                productToListView.add(product);
                productListViewAdapter.notifyDataSetChanged();
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
        productListView = mRootView.findViewById(R.id.productListView);
        productListViewAdapter = new RowProductActivity(mRootView.getContext(),R.layout.activity_row_product,productToListView);
        productListView.setAdapter(productListViewAdapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedPrd = productToListView.get(position);
                Intent gotoDetail = new Intent(mRootView.getContext(),DetailProductActivity.class);

                gotoDetail.putExtra("prd",selectedPrd);
                startActivity(gotoDetail);
            }
        });

    }

    private void setupSpinner() {
        idGroup.add("");
        nameGroup.add("Tất cả");
        final DatabaseReference groupCusRef = new Reference().getGroupProductRef();
        groupProductSpinner = mRootView.findViewById(R.id.groupProductSpinner);
        final ArrayAdapter groupProductAdapter = new ArrayAdapter(mRootView.getContext(),android.R.layout.simple_spinner_item,nameGroup);
        groupProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupProductSpinner.setAdapter(groupProductAdapter);


        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                groupProductAdapter.notifyDataSetChanged();
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

        groupProductSpinner.setSelection(0,false);
        groupProductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productToListView.clear();
                if(position == 0){
                    for(Product p: allProduct){
                        productToListView.add(p);
                        productListViewAdapter.notifyDataSetChanged();
                    }
                }else {
                    for(Product p: allProduct){
                        if(p.groupProduct.equals(idGroup.get(position))){
                            productToListView.add(p);
                            productListViewAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        final EditText searchProductET = mRootView.findViewById(R.id.searchProductEditText);
        searchProductET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = searchProductET.getText().toString();
                searPrd(key);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button gotoCreateProduct = mRootView.findViewById(R.id.gotoAddProductButton);
        gotoCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mRootView.getContext(),CreateProductActivity.class);
                startActivity(i);
            }
        });
    }

    private void searPrd(String key) {
        productToListView.clear();
        for (Product prd : allProduct){
            if (prd.nameProduct.contains(key) || prd.idProduct.contains(key)){
                if(!productToListView.contains(prd)){
                    productToListView.add(prd);
                }
                productListViewAdapter.notifyDataSetChanged();
            }
        }

    }
}
