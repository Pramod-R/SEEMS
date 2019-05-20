package com.sapthagiri.www.sap;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class Details_Product_Adapter extends RecyclerView.Adapter<Details_Product_Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<product_details> productList;
    public Details_Product_Adapter(Context mCtx, List<product_details> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.studnet_details_product_list, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        product_details product= productList.get(position);
        //loading the image

        holder.tx2.setText(product.getFullname());
       holder.tx3.setText(product.getUsnno());

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tx2, tx3, tx4, tx5, tx6, tx1,tx7;
        ImageView imageView;
        public ProductViewHolder(View itemView) {
            super(itemView);

           tx2 = itemView.findViewById(R.id.tx2);
            tx3 = itemView.findViewById(R.id.tx3);

        }
    }
}