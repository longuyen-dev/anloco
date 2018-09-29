package com.example.mrlong.anloco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import Objects.ProductToCart;

public class DetailInvoiceActivity extends AppCompatActivity {

    ListView detailListView;
    ArrayAdapter<ProductToCart> detailAdapter;
    ArrayList<ProductToCart> listDetail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);



        Intent i = getIntent();
        Map<String, ProductToCart> detail = (Map<String, ProductToCart>) i.getSerializableExtra("detail");
        for(ProductToCart p:detail.values()){
            System.out.println(p.nameProduct);
            listDetail.add(p);
        }
        initView();

    }

    private void initView() {
        detailListView = findViewById(R.id.detailInvoiceListView);
        detailAdapter = new RowDetailInvoiceActivity(DetailInvoiceActivity.this,R.layout.activity_row_detail_invoice,listDetail);
        detailListView.setAdapter(detailAdapter);
        detailAdapter.notifyDataSetChanged();
    }
}
