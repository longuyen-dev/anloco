package com.example.mrlong.anloco;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Contains.Reference;
import Objects.Customer;
import Objects.Invoice;
import Objects.Product;
import Objects.ProductToCart;

public class InvoiceActivity extends Fragment {
    private View mRootView;
    EditText dateET,idCusET,addressCusET,phoneCusET,idOfInvoiceET,idProductInvoiceET,pricePrdET,quantumPrdET;
    TextView totalPayET;
    Button insertInvoiceBT,addToCartBT;
    ListView cartListView;

    AutoCompleteTextView searchProductET,searchCustomerET;

    ArrayList<Customer> allCustomer = new ArrayList<>();
    List<String> nameCustomer = new ArrayList<>();

    ArrayList<Product> allProduct = new ArrayList<>();
    List<String> nameProduct = new ArrayList<>();


    ArrayAdapter searchCusAdapter;
    ArrayAdapter searchPrdAdapter;

    DatabaseReference invoiceRef = new Reference().getInvoiceRef();
    RowProductCartActivity rowProductCartAdapter;

    String groupPrice = "price1";

    ArrayList<ProductToCart> productToCartsList = new ArrayList<>();

    Dialog payDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_invoice,container,false);

        initView();
        observerCustomer();
        observerProduct();
        setupSearchCustomer();
        setupSearchProduct();
        setupCartListView();

        payDialog = new Dialog(mRootView.getContext());

        return mRootView;
    }
    private void setupCartListView() {
        rowProductCartAdapter = new RowProductCartActivity(mRootView.getContext(),R.layout.activity_row_product_cart,productToCartsList);
        cartListView = mRootView.findViewById(R.id.cartListView);
        cartListView.setAdapter(rowProductCartAdapter);


        cartListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                productToCartsList.remove(position);
                rowProductCartAdapter.notifyDataSetChanged();
                calculateTotal();
                return true;
            }
        });
    }

    private void setupSearchProduct() {
        searchProductET = mRootView.findViewById(R.id.searchProductInvoiceEditText);
        searchPrdAdapter = new ArrayAdapter(mRootView.getContext(),android.R.layout.simple_spinner_dropdown_item,nameProduct);
        searchProductET.setAdapter(searchPrdAdapter);

        searchProductET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProductET.showDropDown();
            }
        });

        searchProductET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) searchPrdAdapter.getItem(position);
                for(Product p: allProduct){
                    if(p.nameProduct.equals(name)){
                        idProductInvoiceET.setText(p.idProduct);
                        String price = "0";
                        switch (groupPrice){
                            case "price1": price = p.price1;
                                break;
                            case "price2": price = p.price2;
                                break;
                            case "price3": price = p.price3;
                                break;
                            case "price4": price = p.price4;
                                break;
                            case "present": price = p.present;
                                break;
                            default: price = p.present;
                        }

                        pricePrdET.setText(price);
                    }
                }
            }
        });
    }
    private void observerProduct() {
        allProduct.clear();
        DatabaseReference prdRef = new Reference().getProductRef();
        prdRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product p = dataSnapshot.getValue(Product.class);
                allProduct.add(p);
                nameProduct.add(p.nameProduct);
                searchPrdAdapter.notifyDataSetChanged();
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
        searchCustomerET = mRootView.findViewById(R.id.searchCusInvoiceET);
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
                String name = (String) searchCusAdapter.getItem(position);
                for(Customer c:allCustomer){
                    if(c.nameCustomer.equals(name)){
                        idCusET.setText(c.idCustomer);
                        phoneCusET.setText(c.phoneCustomer);
                        addressCusET.setText(c.addressCustomer);
                        groupPrice = c.priceGroup;
                    }
                }

            }
        });//set click on item customer.

    }
    private void observerCustomer() {
        allCustomer.clear();
        DatabaseReference cusRef = new Reference().getCustomerRef();
        cusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Customer c = dataSnapshot.getValue(Customer.class);
                allCustomer.add(c);
                nameCustomer.add(c.nameCustomer);
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

    private void initView() {
        dateET = mRootView.findViewById(R.id.dateInvoiceET);
        idCusET = mRootView.findViewById(R.id.idCusInvoiceET);
        addressCusET = mRootView.findViewById(R.id.addressCusInvoiceET);
        phoneCusET = mRootView.findViewById(R.id.phoneCusInvoiceET);
        idOfInvoiceET = mRootView.findViewById(R.id.idInvoiceET);
        idProductInvoiceET = mRootView.findViewById(R.id.idProductInvoiceEditText);
        pricePrdET = mRootView.findViewById(R.id.priceET);
        quantumPrdET = mRootView.findViewById(R.id.quantumET);
        totalPayET = mRootView.findViewById(R.id.totalPayET);


        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dpf = new DatePickerFragment().newInstance();
                dpf.setCallBack(onDate);
                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });
        insertInvoiceBT = mRootView.findViewById(R.id.gotoPayBT);
        insertInvoiceBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idOfInvoiceET.getText().toString().equals("") || idCusET.getText().toString().equals("")){
                    Toast.makeText(mRootView.getContext(),"Chọn khách và ngày trước",Toast.LENGTH_SHORT).show();
                }else {
                    showPayDialog(v);
                }

            }
        });//insert new invoice.

        addToCartBT = mRootView.findViewById(R.id.addToCartBT);
        addToCartBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(quantumPrdET.getText().toString()) * Integer.parseInt(pricePrdET.getText().toString()) ;
                ProductToCart ptc = new ProductToCart(idProductInvoiceET.getText().toString(),searchProductET.getText().toString() , pricePrdET.getText().toString(), quantumPrdET.getText().toString(),String.valueOf(total));
                productToCartsList.add(ptc);
                rowProductCartAdapter.notifyDataSetChanged();
                calculateTotal();

                idProductInvoiceET.setText("");
                searchProductET.setText("");
                pricePrdET.setText("0");
                quantumPrdET.setText("0");
            }
        });// add to cart button.


        //selectProduct area

    }// init view
    public void showPayDialog(View v){
        payDialog.setContentView(R.layout.activity_pay);
        final EditText total,pay,owe;
        Button insertInvoiceBtn;
        insertInvoiceBtn = payDialog.findViewById(R.id.insertInvoiceBtn);
        total = payDialog.findViewById(R.id.totalPayET);
        pay = payDialog.findViewById(R.id.payET);
        owe = payDialog.findViewById(R.id.oweET);
        total.setText(totalPayET.getText());
        pay.setText(totalPayET.getText());
        owe.setText("0");

        final int totalMoney = Integer.parseInt(totalPayET.getText().toString());
        pay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                int payMoney = 0;
//                if(pay.getText().equals("")){
//                    payMoney = 0;
//                    pay.setText("0");
//                }else {
////                    payMoney = Integer.parseInt(pay.getText().toString());
//                }

//                int oweMoney = totalMoney - payMoney;
//                owe.setText(String.valueOf(oweMoney));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(pay.getText().toString().equals("")){
                    pay.setText("0");
                }
                int payMoney = Integer.parseInt(pay.getText().toString());
                int oweMoney = totalMoney - payMoney;
                owe.setText(String.valueOf(oweMoney));

            }
        });
        insertInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invoice i = new Invoice(idOfInvoiceET.getText().toString(),dateET.getText().toString(),idCusET.getText().toString(), total.getText().toString(), pay.getText().toString(), owe.getText().toString());
                invoiceRef.child(idOfInvoiceET.getText().toString()).setValue(i);

                for(ProductToCart ptc: productToCartsList){
                    invoiceRef.child(idOfInvoiceET.getText().toString()).child("detail").push().setValue(ptc);

                }
                productToCartsList.clear();
                calculateTotal();
                rowProductCartAdapter.notifyDataSetChanged();
                payDialog.dismiss();
            }
        });

        payDialog.show();
    }
    private void calculateTotal() {
        int total = 0;
        if (productToCartsList.size() == 0){
            total = 0;
            totalPayET.setText(String.valueOf(total));
        }else {
            for(ProductToCart ptcl: productToCartsList){
                total += Integer.parseInt(ptcl.total);
            }
            totalPayET.setText(String.valueOf(total));
        }
    }
    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            final String dateToSet = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth);
            dateET.setText(dateToSet);
            final String dateString = String.valueOf(year) + String.valueOf(monthOfYear)
                    + String.valueOf(dayOfMonth);
            invoiceRef.orderByChild("dateInvoice").equalTo(dateToSet).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int lastId = (int) (dataSnapshot.getChildrenCount() + 1);
                    String lastIdStr = String.valueOf(lastId);
                    createIdInvoice(dateString,lastIdStr);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    };

    private void createIdInvoice(String dateString, String date) {
        int dateInt = Integer.parseInt(date);
        if(dateInt < 10){
            date = "00"+date;
        }else{
            if(dateInt < 100){
                date = "0"+date;
            }
        }
        String id = "PXB"+dateString+date;
        idOfInvoiceET.setText(id);
    }
}

