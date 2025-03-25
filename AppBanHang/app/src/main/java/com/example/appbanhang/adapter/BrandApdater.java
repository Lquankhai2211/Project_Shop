package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.Brand;

import java.util.List;

public class BrandApdater extends BaseAdapter {

    List<Brand> brands;
    Context context;

    public BrandApdater(List<Brand> brands, Context context) {
        this.brands = brands;
        this.context = context;
    }

    @Override
    public int getCount() {
        return brands.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView textProductName;
        ImageView imgProduct;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.textProductName = view.findViewById(R.id.item_productName);
            viewHolder.imgProduct = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textProductName.setText(brands.get(i).getBrand_name());
        Glide.with(context).load(brands.get(i).getBrand_image()).into(viewHolder.imgProduct);
        return view;
    }
}
