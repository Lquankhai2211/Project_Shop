package com.example.appbanhang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.models.Cart;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PayActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView pay_totalprice, pay_phone, pay_email;
    EditText pay_address;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    ApiBanHang apiBanHang;
    AppCompatButton btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initControl();
    }
    private void initControl(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_address = pay_address.getText().toString().trim();
                if (TextUtils.isEmpty(str_address)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ",Toast.LENGTH_SHORT).show();
                }else {
                    String str_email = pay_email.getText().toString().trim();
                    String str_phone = pay_phone.getText().toString().trim();
                    int id = Utils.user_current.getId();
                    int totalQuantity = calculateTotalQuantity();
                    String orderDetailsJson = new GsonBuilder().setPrettyPrinting().create().toJson(Utils.carts);
                    Log.d("ORDER_DETAILS", orderDetailsJson);
                    String totalPrice = pay_totalprice.getText().toString().replace(",", "").trim(); // Loại bỏ dấu ,
                    double price = Double.parseDouble(totalPrice);
                    compositeDisposable.add(apiBanHang.createOrder(str_email,str_phone,  String.valueOf(price),id,str_address,totalQuantity,orderDetailsJson)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
                                        Utils.carts.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                                        startActivity(intent);
                                        finish();
                                    },throwable -> {
                                        Log.e("PayActivity", "Lỗi khi gọi API: " + throwable.getMessage());
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                            ));
                }
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        double totalprice = getIntent().getDoubleExtra("totalprice",0);
        pay_totalprice.setText(decimalFormat.format(totalprice));
        pay_phone.setText(Utils.user_current.getPhone());
        pay_email.setText(Utils.user_current.getEmail());
    }
    private int calculateTotalQuantity() {
        int totalQuantity = 0;
        for (Cart cart : Utils.carts) {
            totalQuantity += cart.getAmount();
        }
        return totalQuantity;
    }
    private void initView(){
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar);
        pay_totalprice = findViewById(R.id.pay_totalprice);
        pay_phone = findViewById(R.id.pay_phone);
        pay_address = findViewById(R.id.pay_address);
        pay_email = findViewById(R.id.pay_email);
        btnOrder = findViewById(R.id.btnOrder);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}