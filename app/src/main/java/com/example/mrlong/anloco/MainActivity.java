package com.example.mrlong.anloco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Adapter.TabLayoutAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    SharedPreferences checkLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLogin = getSharedPreferences("checkLogin", MODE_PRIVATE);


        if(checkLogin.contains("name")){
            initView();
        }else {
            logout();
            System.out.println("not login");
        }

    }

    private void logout() {
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabLayoutAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
