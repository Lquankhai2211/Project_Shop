package com.example.appbanhang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.Cart;
import com.example.appbanhang.models.Product;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {
    TextView productName, productPrice, productDescription;
    Button buttonAdd;
    ImageView productImg;
    Product product;
    Spinner spinner;
    Toolbar toolbar;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        ActionToolBar();
        InitData();
        InitControl();
    }
    private void InitControl(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }
        });
    }
    private void addCart(){
        if (Utils.carts.size()>0){
            boolean flag = false;
            int amount = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.carts.size(); i++) {
                if (Utils.carts.get(i).getProduct_id() == product.getId()) {
                    Utils.carts.get(i).setAmount(amount + Utils.carts.get(i).getAmount());
                    double product_price = Double.parseDouble(product.getProduct_price()) * Utils.carts.get(i).getAmount();
                    Utils.carts.get(i).setProduct_price(product_price);
                    flag = true;
                }
            }
            if (flag ==false){
                double product_price = Double.parseDouble(product.getProduct_price());
                Cart cart = new Cart();
                cart.setProduct_price(product_price);
                cart.setAmount(amount);
                cart.setProduct_id(product.getId());
                cart.setProduct_name(product.getProduct_name());
                cart.setProduct_image(product.getProduct_image());
                Utils.carts.add(cart);
            }
        }else {
            int amount = Integer.parseInt(spinner.getSelectedItem().toString());
            double product_price = Double.parseDouble(product.getProduct_price());
            Cart cart = new Cart();
            cart.setProduct_price(product_price);
            cart.setAmount(amount);
            cart.setProduct_id(product.getId());
            cart.setProduct_name(product.getProduct_name());
            cart.setProduct_image(product.getProduct_image());
            Utils.carts.add(cart);

        }
        int totalQuantity = 0;
        for (Cart cart : Utils.carts) {
            totalQuantity += cart.getAmount();
        }
        badge.setText(String.valueOf(totalQuantity));
    }
    private void InitData(){
        product = (Product) getIntent().getSerializableExtra("detail");
        productName.setText(product.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        productPrice.setText("Giá: "+decimalFormat.format(Double.parseDouble(product.getProduct_price()))+" VNĐ");
        productDescription.setText(product.getProduct_description());
        Glide.with(getApplicationContext()).load(product.getProduct_image()).into(productImg);
        Integer[] number = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpinner= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,number);
        spinner.setAdapter(adapterSpinner);
    }
    private void Mapping(){
        productName = findViewById(R.id.detail_product_name);
        productPrice = findViewById(R.id.detail_product_price);
        productDescription =findViewById(R.id.detail_product_description);
        buttonAdd = findViewById(R.id.btn_add_cart);
        productImg = findViewById(R.id.img_detail);
        spinner = findViewById(R.id.detail_spinner);
        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.cart_amount);
        FrameLayout frameCart = findViewById(R.id.frameCart);
        frameCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cart);
            }
        });
        if(Utils.carts!=null){
            int totalQuantity = 0;
        for (Cart cart : Utils.carts) {
            totalQuantity += cart.getAmount();
        }
        badge.setText(String.valueOf(totalQuantity));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}