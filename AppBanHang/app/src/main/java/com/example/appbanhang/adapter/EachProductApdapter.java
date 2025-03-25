package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activities.DetailProductActivity;
import com.example.appbanhang.models.Product;

import java.text.DecimalFormat;
import java.util.List;

public class EachProductApdapter extends RecyclerView.Adapter<EachProductApdapter.MyViewHolder> {
    Context context;
    List<Product> products;

    public EachProductApdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_act,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.pName.setText(product.getProduct_name());
        holder.pDescription.setText(product.getProduct_description());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.pPrice.setText("Giá: "+decimalFormat.format(Double.parseDouble(product.getProduct_price()))+" VNĐ");
        Glide.with(context).load(product.getProduct_image()).into(holder.pImage);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("detail",product);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView pName, pPrice, pDescription;
        ImageView pImage;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            pName = itemView.findViewById(R.id.id_item_product_name);
            pPrice = itemView.findViewById(R.id.id_item_product_price);
            pImage = itemView.findViewById(R.id.id_item_product_image);
            pDescription = itemView.findViewById(R.id.id_item_product_description);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view){
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
