package com.example.appbanhang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.models.UserModel;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText email, pass, confirmPass, phone, username;
    AppCompatButton btnRegister;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        intControl();
    }
    private void intControl(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void register(){
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_confPass = confirmPass.getText().toString().trim();
        String str_phone= phone.getText().toString().trim();
        String str_username= username.getText().toString().trim();

        if (TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập email",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_confPass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập lại mật khẩu",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_phone)) {
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập số điện thoại",Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên đăng nhập",Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_confPass)){
                compositeDisposable.add(apiBanHang.register(str_email,str_pass, str_username,str_phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.user_current.setEmail(str_email);
                                        Utils.user_current.setPassword(str_pass);
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                },throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                        ));
            }else{
                Toast.makeText(getApplicationContext(),"Mật khẩu chưa khớp",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.register_email);
        pass = findViewById(R.id.register_pass);
        confirmPass = findViewById(R.id.register_conFirmPass);
        phone = findViewById(R.id.register_phone);
        username = findViewById(R.id.register_username);
        btnRegister = findViewById(R.id.btnRegister);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}