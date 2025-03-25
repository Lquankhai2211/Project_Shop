package com.example.appbanhang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.EachProductApdapter;
import com.example.appbanhang.adapter.ProductAdapter;
import com.example.appbanhang.models.Product;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EachProductApdapter productAdapter;
    List<Product> products;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    int brandId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        brandId = getIntent().getIntExtra("brandId", -1);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Mapping();
        ActionBar();

        if (brandId != -1) {
            getProductsByBrandId(brandId);
        } else {
            Toast.makeText(this, "Không tìm thấy thương hiệu", Toast.LENGTH_SHORT).show();
        }
    }

    private void getProductsByBrandId(int brandId){
        compositeDisposable.add(apiBanHang.getProductsByBrandId(brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()) {
                                products = productModel.getResult();
                                productAdapter = new EachProductApdapter(getApplicationContext(), products);
                                recyclerView.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),
                                    "Lỗi: " + throwable.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed(); // Xử lý quay lại khi bấm vào nút back
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Quay lại màn hình trước đó
    }
    private void Mapping() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        products = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
