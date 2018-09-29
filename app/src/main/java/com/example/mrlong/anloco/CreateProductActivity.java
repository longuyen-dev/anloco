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
import Objects.Product;

public class CreateProductActivity extends AppCompatActivity {

    EditText idProductET, nameProductET, price1ET, price2ET, price3ET, price4ET, idGroupProductET, nameGroupET;
    Spinner groupProductSP;
    Button addProduct,addGroupProduct;
    ListView groupProductListView;
    ArrayAdapter groupListViewAdapter;

    List<String> idGroup = new ArrayList<>();
    List<String> nameGroup = new ArrayList<>();

    final DatabaseReference groupCusRef = new Reference().getGroupProductRef();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        initView();
        setupListView();
    }

    private void setupListView() {
        groupProductListView = (ListView)findViewById(R.id.groupProductListView);
        groupListViewAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameGroup);
        groupProductListView.setAdapter(groupListViewAdapter);

        groupProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idGroupProductET.setText(idGroup.get(position));
                nameGroupET.setText(nameGroup.get(position));
            }
        });
    }

    private void initView() {

        idGroupProductET = findViewById(R.id.idGroupProductEditText);
        nameGroupET = findViewById(R.id.nameGroupProductEditText);


        groupProductSP = (Spinner) findViewById(R.id.groupProductSpinner);
        final ArrayAdapter groupProductAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,nameGroup);
        groupProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupProductSP.setAdapter(groupProductAdapter);



        groupCusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                idGroup.add(dataSnapshot.getKey());
                nameGroup.add((String) dataSnapshot.getValue());
                groupProductAdapter.notifyDataSetChanged();
                groupListViewAdapter.notifyDataSetChanged();
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


        idProductET = findViewById(R.id.idProductEditText);
        nameProductET = findViewById(R.id.nameProductEditText);
        price1ET = findViewById(R.id.price1EditText);
        price2ET = findViewById(R.id.price2EditText);
        price3ET = findViewById(R.id.price3EditText);
        price4ET = findViewById(R.id.price4EditText);
        addProduct = findViewById(R.id.insertNewProductButton);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idProductET.getText().toString().equals("") || nameProductET.getText().toString().equals("")){
                    Toast.makeText(CreateProductActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();

                }else {
                    String name = nameProductET.getText().toString();
                    String id = idProductET.getText().toString();
                    String p1 = price1ET.getText().toString();
                    String p2 = price2ET.getText().toString();
                    String p3 = price3ET.getText().toString();
                    String p4 = price4ET.getText().toString();
                    int p = groupProductSP.getSelectedItemPosition();
                    String group = idGroup.get(p);
                    Product prd = new Product(name,id,group,p1,p2,p3,p4,"0");

                    final DatabaseReference productRef = new Reference().getProductRef();
                    productRef.child(id).setValue(prd);
                }

            }
        });

        addGroupProduct = (Button) findViewById(R.id.insertNewGroupProductButton);
        addGroupProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idGroupProductET.getText().toString().equals("") || nameGroupET.getText().toString().equals("")){
                    Toast.makeText(CreateProductActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();

                }else {
                    groupCusRef.child(idGroupProductET.getText().toString()).setValue(nameGroupET.getText().toString());
                }
            }
        });


    }
}
