package com.sapthagiri.www.sap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class Faculty_product_adapter extends RecyclerView.Adapter<Faculty_product_adapter.ProductViewHolder> {


    private Context mCtx;
    private List<Faculty_product> productList;


    public Faculty_product_adapter(Context mCtx, List<Faculty_product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.faculty_product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Faculty_product Faculty_product = productList.get(position);

        //loading the image

       }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}