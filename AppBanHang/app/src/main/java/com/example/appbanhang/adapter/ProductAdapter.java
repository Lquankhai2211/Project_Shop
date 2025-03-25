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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txtName.setText(product.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Giá: "+decimalFormat.format(Double.parseDouble(product.getProduct_price()))+" VNĐ");
        Glide.with(context).load(product.getProduct_image()).into(holder.imgProduct);
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
        TextView txtPrice, txtName;
        ImageView imgProduct;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.id_product_name);
            txtPrice = itemView.findViewById(R.id.id_product_price);
            imgProduct = itemView.findViewById(R.id.id_product_image);
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
