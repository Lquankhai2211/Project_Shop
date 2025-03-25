package com.example.appbanhang.retrofit;

import io.reactivex.rxjava3.core.Observable;

import com.example.appbanhang.models.BrandModel;
import com.example.appbanhang.models.ProductModel;
import com.example.appbanhang.models.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiBanHang {
    @GET("getBrand.php")
    Observable<BrandModel> getBrand();
    @GET("getProduct.php")
    Observable<ProductModel> getProducts();
    @GET("getByBrandId.php")
    Observable<ProductModel> getProductsByBrandId(@Query("brandId") Integer brandId);

    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
            @Field("phone") String phone
    );
    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("pass") String password
    );

    @POST("order.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("total_price") String total_price,
            @Field("user_id") int user_id,
            @Field("address") String address,
            @Field("amount") int amount,
            @Field("order_details") String order_details
    );
}
