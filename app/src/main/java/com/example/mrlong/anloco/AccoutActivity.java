package com.example.mrlong.anloco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Contains.Reference;
import Objects.User;

import static android.content.Context.MODE_PRIVATE;

public class AccoutActivity extends Fragment {
    View mRootView;
    ListView accountListView;
    EditText uTV,pTV;
    Button logoutButton,addButton,deleteButton;
    Spinner ruleSpiner;
    SharedPreferences checkLogin;

    ArrayList<String> listRule = new ArrayList<>();
    ArrayAdapter<String> ruleAdapter;

    ArrayList<userTmp> listUser = new ArrayList<>();




    DatabaseReference userRef = new Reference().getUsersRef();
    RowAccountActivity listviewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_accout,container,false);

        checkLogin = mRootView.getContext().getSharedPreferences("checkLogin", MODE_PRIVATE);

        if(checkLogin.getString("name","").equals("admin")){
            initView();
            setupListView();
            setupSpinner();
            observerUsers();
        }else {
            Toast.makeText(mRootView.getContext(),"Chức năng dành riêng cho người quản trị",Toast.LENGTH_SHORT).show();
        }

        return mRootView;
        
    }

    private void setupSpinner() {
        listRule.add("admin");
        listRule.add("Nhân viên");
        listRule.add("Nhân viên 1");

        ruleSpiner = mRootView.findViewById(R.id.ruleSpinner);
        ruleAdapter = new ArrayAdapter<String>(mRootView.getContext(),android.R.layout.simple_spinner_item,listRule);
        ruleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ruleSpiner.setAdapter(ruleAdapter);
    }

    private void observerUsers() {
        listUser.clear();
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User u = dataSnapshot.getValue(User.class);
                userTmp uT = new userTmp(dataSnapshot.getKey(),u.rule);
                if(!listUser.equals(uT))
                {
                    listUser.add(uT);
                    listviewAdapter.notifyDataSetChanged();
                }



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
        accountListView = mRootView.findViewById(R.id.accountListView);

//        listviewAdapter = new ArrayAdapter<String>(mRootView.getContext(),android.R.layout.simple_list_item_1,listName);
        listviewAdapter = new RowAccountActivity(mRootView.getContext(),R.layout.activity_row_account,listUser);

        accountListView.setAdapter(listviewAdapter);

        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userTmp sU = listUser.get(position);
                uTV.setText(sU.username);
                ruleSpiner.setSelection(listRule.indexOf(sU.rule));
            }
        });
    }

    private void initView() {
        uTV = mRootView.findViewById(R.id.usernameEditText);
        pTV = mRootView.findViewById(R.id.passwordEditText);

        logoutButton = mRootView.findViewById(R.id.logoutButton);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = checkLogin.edit();
                ed.remove("name").commit();

                logout();

            }
        });

        addButton = mRootView.findViewById(R.id.addEditButon);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = uTV.getText().toString();
                String p = pTV.getText().toString();
                String check = u + p;
                String r = listRule.get(ruleSpiner.getSelectedItemPosition());
                User newUser = new User(check,p,r);

                userRef.child(u).setValue(newUser);


            }
        });

        //delete User
        deleteButton = mRootView.findViewById(R.id.deleteButon);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uTV.getText().toString().equals("admin")){
                    Toast.makeText(mRootView.getContext(),"Không thể xoá",Toast.LENGTH_SHORT).show();
                }else {
                    for(userTmp u : listUser){
                        if(u.username.equals(uTV.getText().toString())){
                            listUser.remove(u);
                            listviewAdapter.notifyDataSetChanged();
                        }
                    }
                    userRef.child(uTV.getText().toString()).removeValue();

                }
            }
        });
    }

    private void logout() {
        Intent login = new Intent(mRootView.getContext(),LoginActivity.class);
        startActivity(login);
    }
}
class userTmp {
    String username, rule;

    public userTmp(String username, String rule) {
        this.username = username;
        this.rule = rule;
    }
}
