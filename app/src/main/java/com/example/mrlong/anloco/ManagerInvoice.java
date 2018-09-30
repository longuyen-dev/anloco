package com.example.mrlong.anloco;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Contains.Reference;
import Objects.Customer;
import Objects.Invoice;
import Objects.ProductToCart;

public class ManagerInvoice extends Fragment {

    View mRootView;
    EditText fromDateET, toDateET;
    AutoCompleteTextView searchCustomerET;
    Button fillterBT;
    ListView invoiceListView;

    ArrayAdapter invoiceLVAdapter;
    ArrayList<Invoice> listInvoice = new ArrayList<>();
    ArrayList<Invoice> listInvoiceToListView = new ArrayList<>();

    ArrayList<Customer> allCustomer = new ArrayList<>();
    List<String> nameCustomer = new ArrayList<>();
    List<String> idCustomer = new ArrayList<>();

    ArrayAdapter searchCusAdapter;

    int swich = 0;

    DatabaseReference invoiceRef = new Reference().getInvoiceRef();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_manager_invoice,container,false);

        initView();
        observerInvoice();
        observerCustomer();
        setupSearchCustomer();
        setupListView();

        return mRootView;
    }

    private void observerCustomer() {

        DatabaseReference cusRef = new Reference().getCustomerRef();
        cusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Customer c = dataSnapshot.getValue(Customer.class);
                allCustomer.add(c);
                nameCustomer.add(c.nameCustomer);
                idCustomer.add(c.idCustomer);
                searchCusAdapter.notifyDataSetChanged();
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

    private void setupSearchCustomer() {

        searchCustomerET = mRootView.findViewById(R.id.searchCustomerEditText);
        searchCusAdapter = new ArrayAdapter(mRootView.getContext(), android.R.layout.simple_spinner_dropdown_item,nameCustomer);
        searchCustomerET.setAdapter(searchCusAdapter);

        searchCustomerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCustomerET.showDropDown();
            }
        });// set show drop down search customer.

        searchCustomerET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = nameCustomer.indexOf(searchCusAdapter.getItem(position));
                String ido = idCustomer.get(i);
                searchCustomerET.setText(ido);
            }
        });




    }

    private void observerInvoice() {
        invoiceRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                System.out.println(dataSnapshot.getValue());
                Invoice ivl = dataSnapshot.getValue(Invoice.class);
                listInvoice.add(ivl);
                listInvoiceToListView.add(ivl);
                invoiceLVAdapter.notifyDataSetChanged();
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
        invoiceListView = mRootView.findViewById(R.id.invoiceListView);
        invoiceLVAdapter = new RowInvoiceActivity(mRootView.getContext(),R.layout.activity_row_invoice,listInvoiceToListView);
        invoiceListView.setAdapter(invoiceLVAdapter);

        invoiceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Invoice selectInvoice = listInvoiceToListView.get(position);
                Intent gotoDetail = new Intent(mRootView.getContext(),DetailInvoiceActivity.class);
                Map<String, ProductToCart> detail = selectInvoice.detail;
                gotoDetail.putExtra("detail", (Serializable) detail);
                startActivity(gotoDetail);
            }
        });
    }

    private void initView() {
        fromDateET = mRootView.findViewById(R.id.fromDateET);
        fromDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swich = 0;
                DatePickerFragment dpf = new DatePickerFragment().newInstance();
                InvoiceActivity invoiceActivity = new InvoiceActivity();

                dpf.setCallBack(onDate);
                dpf.show(getFragmentManager().beginTransaction(),"DatePickerFragment");
            }
        });

        toDateET = mRootView.findViewById(R.id.toDateET);
        toDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swich = 1;
                DatePickerFragment dpf = new DatePickerFragment().newInstance();
                InvoiceActivity invoiceActivity = new InvoiceActivity();

                dpf.setCallBack(onDate);
                dpf.show(getFragmentManager().beginTransaction(),"DatePickerFragment");

            }
        });


        fillterBT = mRootView.findViewById(R.id.filterInvoiceBT);
        fillterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInvoiceToListView.clear();
                String from = fromDateET.getText().toString();
                if(from.equals(""))from = "0";
                String to = toDateET.getText().toString();
                if(to.equals(""))to = "Z";
                String cus = searchCustomerET.getText().toString();


                for(Invoice ivl: listInvoice){
                    if(ivl.dateInvoice.compareTo(from) >= 0 && ivl.dateInvoice.compareTo(to) <= 0){
                       if(cus.equals("")){
                           listInvoiceToListView.add(ivl);
                           invoiceLVAdapter.notifyDataSetChanged();
                       }else{
                           if(ivl.customer.equals(cus)){
                               listInvoiceToListView.add(ivl);
                               invoiceLVAdapter.notifyDataSetChanged();
                           }else{
                               continue;
                           }
                       }
                    }
                }

            }
        });

    }
    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String month = String.valueOf(monthOfYear+1);
            String day = String.valueOf(dayOfMonth);
            if(monthOfYear < 10) month = "0"+month;
            if(dayOfMonth < 10) day = "0"+day;
            String dateToSet = String.valueOf(year) + "-" + month
                    + "-" + day;

            if(swich == 0){
                fromDateET.setText(dateToSet);
            }else {
                toDateET.setText(dateToSet);
            }

        }

    };
}


