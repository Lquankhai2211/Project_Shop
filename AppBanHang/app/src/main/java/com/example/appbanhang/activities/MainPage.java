package com.example.appbanhang.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.BrandApdater;
import com.example.appbanhang.adapter.ProductAdapter;
import com.example.appbanhang.models.Brand;
import com.example.appbanhang.models.Cart;
import com.example.appbanhang.models.Product;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPage extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    BrandApdater brandApdater;
    List<Brand> brands;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<Product> products;
    ProductAdapter productAdapter;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        ActionBar();
        if (isConnected(this)){
            ActionViewFlipper();
            getBrand();
            getProducts();
            getEventClick();
        }else {
            Toast.makeText(getApplicationContext(),"Không có internet, vui lòng kết nối internet",Toast.LENGTH_LONG).show();

        }
        onResume();
    }
    private void getProducts(){
        compositeDisposable.add(apiBanHang.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                products = productModel.getResult();
                                productAdapter = new ProductAdapter(getApplicationContext(),products);
                                recyclerView.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được với server"+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void getBrand(){
        compositeDisposable.add(apiBanHang.getBrand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        brandModel -> {
                            if(brandModel.isSuccess()){
                                brands = brandModel.getResult();
                                brandApdater = new BrandApdater(brands,getApplicationContext());
                                listView.setAdapter(brandApdater);
                            }
                        }
                ));
    }
    private void ActionViewFlipper(){
        List<String> slides = new ArrayList<>();
        slides.add("https://product.hstatic.net/1000288177/product/12242-3_68ea765ee12448338286bd5ed04c95ad_master.jpg");
        slides.add("https://product.hstatic.net/1000288177/product/upload_5985059e63d84869b2276a7ff53ceb58_0f8260c2784c428fb79060d78210691f_master.jpg");
        slides.add("https://product.hstatic.net/1000288177/product/upload_493ddfb2d220494fbb706e069e4e2501_88824bf2a3ca4c23a3ae7035d56e82f8_master.jpg");
        for (int i =0; i<slides.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(slides.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (badge != null) {
            int totalQuantity = 0;
            if (Utils.carts != null) {
                for (Cart cart : Utils.carts) {
                    totalQuantity += cart.getAmount();
                }
            }
            badge.setText(String.valueOf(totalQuantity));
        }
    }

    private void Mapping(){
        toolbar = findViewById(R.id.toolbarMainPage);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerView = findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listviewMainPage);
        drawerLayout = findViewById(R.id.drawerLayout);
        brands = new ArrayList<>();
        products = new ArrayList<>();
        if (Utils.carts==null){
            Utils.carts =new ArrayList<>();
        }
        badge = findViewById(R.id.cart_amount);
        if(Utils.carts!=null){
            int totalQuantity = 0;
            for (Cart cart : Utils.carts) {
                totalQuantity += cart.getAmount();
            }
            badge.setText(String.valueOf(totalQuantity));
        }
        FrameLayout frameCart = findViewById(R.id.frameCart);
        frameCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cart);
            }
        });
    }
    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi!=null && wifi.isConnected()) || (mobile!=null&&mobile.isConnected())){
            return true;
        }else {
            return false;
        }
    }
    @Override
    protected void onDestroy(){
       compositeDisposable.clear();
       super.onDestroy();
    }
    private void getEventClick() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            int brandId = brands.get(i).getId(); // Lấy brandId từ danh sách
            if (brandId > 0) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("brandId", brandId); // Truyền brandId qua Intent
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid brandId", Toast.LENGTH_SHORT).show();
            }
        });
    }

}