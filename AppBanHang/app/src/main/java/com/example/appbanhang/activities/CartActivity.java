package com.example.appbanhang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CartAdapter;
import com.example.appbanhang.utils.Utils;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    TextView emptyCart, total_cart;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    CartAdapter cartAdapter;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        InitControl();
    }
    private void InitControl(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.carts.size()==0)
            emptyCart.setVisibility(View.VISIBLE);
        else {
            cartAdapter = new CartAdapter(Utils.carts, getApplicationContext(), (view, pos, value) -> {
                calTotal();
            });
            recyclerView.setAdapter(cartAdapter);
        }
        calTotal();
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("totalprice",total);
                startActivity(intent);
            }
        });
    }
    private void calTotal(){
        for (int i=0;i<Utils.carts.size();i++){
            total = total + (Utils.carts.get(i).getProduct_price()*Utils.carts.get(i).getAmount());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        total_cart.setText(decimalFormat.format(total));

    }
    private void Mapping(){
        emptyCart=findViewById(R.id.empty_cart);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_cart);
        btnBuy = findViewById(R.id.btn_buy);
        total_cart = findViewById(R.id.total_cart);
    }
}