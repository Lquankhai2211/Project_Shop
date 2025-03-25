package com.example.appbanhang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.AmountClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.models.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart> carts;
    AmountClickListener amountClickListener;

    public CartAdapter(List<Cart> carts, Context context, AmountClickListener amountClickListener) {
        this.carts = carts;
        this.context = context;
        this.amountClickListener = amountClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.item_cart_pName.setText(cart.getProduct_name());
        holder.item_cart_amount.setText(cart.getAmount()+"");
        Glide.with(context).load(cart.getProduct_image()).into(holder.item_cart_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cart_price.setText("Giá: "+decimalFormat.format((cart.getProduct_price()))+" VNĐ");
        double price = cart.getAmount()*cart.getProduct_price();
        holder.item_cart_product_totalPrice.setText(decimalFormat.format(price));
        holder.setAmountClickListener((view, pos, value) -> {
            if (value == 1) { // Giảm số lượng
                if (carts.get(pos).getAmount() > 1) {
                    int newAmount = carts.get(pos).getAmount() - 1;
                    carts.get(pos).setAmount(newAmount);
                    holder.item_cart_amount.setText(newAmount + "");
                    double newPrice = newAmount * carts.get(pos).getProduct_price();
                    holder.item_cart_product_totalPrice.setText(decimalFormat.format(newPrice));
                } else {
                    new android.app.AlertDialog.Builder(context)
                            .setTitle("Xác nhận xoá")
                            .setMessage("Bạn có chắc muốn xoá sản phẩm này khỏi giỏ hàng?")
                            .setPositiveButton("Có", (dialog, which) -> {
                                carts.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos, carts.size());
                                if (amountClickListener != null) {
                                    amountClickListener.onClick(view, pos, value);
                                }
                            })
                            .setNegativeButton("Không", null)
                            .show();
                }
            } else if (value == 2) {
                if (carts.get(pos).getAmount() < 11) {
                    int newAmount = carts.get(pos).getAmount() + 1;
                    carts.get(pos).setAmount(newAmount);
                    holder.item_cart_amount.setText(newAmount + "");
                    double newPrice = newAmount * carts.get(pos).getProduct_price();
                    holder.item_cart_product_totalPrice.setText(decimalFormat.format(newPrice));
                }
            }
            if (amountClickListener != null) {
                amountClickListener.onClick(view, pos, value);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_cart_image,item_remove,item_add;
        TextView item_cart_pName, item_cart_price, item_cart_amount, item_cart_product_totalPrice;
        AmountClickListener amountClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            item_cart_image = itemView.findViewById(R.id.item_cart_image);
            item_cart_pName = itemView.findViewById(R.id.item_cart_pName);
            item_cart_price = itemView.findViewById(R.id.item_cart_price);
            item_cart_amount = itemView.findViewById(R.id.item_cart_amount);
            item_cart_product_totalPrice = itemView.findViewById(R.id.item_cart_product_totalPrice);
            item_remove = itemView.findViewById(R.id.item_cart_remove);
            item_add = itemView.findViewById(R.id.item_cart_add);
            item_remove.setOnClickListener(this);
            item_add.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            if(view ==item_remove){
                amountClickListener.onClick(view,getAdapterPosition(),1);
            }else if (view == item_add){
                amountClickListener.onClick(view,getAdapterPosition(),2);
            }
        }
        public void setAmountClickListener(AmountClickListener amountClickListener) {
            this.amountClickListener = amountClickListener;
        }
    }
}
