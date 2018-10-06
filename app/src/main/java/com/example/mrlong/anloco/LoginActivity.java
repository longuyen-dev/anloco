package com.example.mrlong.anloco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Contains.Reference;
import Objects.User;

public class LoginActivity extends AppCompatActivity {

    EditText uTV, pTV;
    Button loginBT;

    DatabaseReference userRef = new Reference().getUsersRef();
    SharedPreferences checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin = getSharedPreferences("checkLogin", MODE_PRIVATE);
        initView();
    }

    private void initView() {
        uTV = findViewById(R.id.usernameEditText);
        pTV = findViewById(R.id.passwordEditText);
        loginBT = findViewById(R.id.loginButton);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uTV.getText().toString();
                String password = pTV.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this,"Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }else {
                    String forCheck = username+password;
                    userRef.orderByChild("check").equalTo(forCheck).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0){
                                User u = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                                loginHandel(u.rule);
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Tài khoảng không tồn tại",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void loginHandel(String name) {

        SharedPreferences.Editor ed = checkLogin.edit();
        ed.putString("name",name).apply();
        Intent login = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(login);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this,"Vui lòng đăng nhập",Toast.LENGTH_SHORT).show();
    }
}
