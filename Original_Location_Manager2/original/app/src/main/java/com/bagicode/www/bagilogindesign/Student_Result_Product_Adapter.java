package com.sapthagiri.www.sap;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class Student_Result_Product_Adapter extends RecyclerView.Adapter<Student_Result_Product_Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<Student_Result_Product> productList;
    public Student_Result_Product_Adapter(Context mCtx, List<Student_Result_Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.student_result_product_list, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Student_Result_Product product= productList.get(position);
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#11C5CAE9"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#33C5CAE9"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        //loading the image
      //  holder.tx1.setText(" "+product.getSid());
        holder.tx2.setText(product.getSubCode());
        holder.tx3.setText(product.getSubject());
        holder.tx4.setText(" "+product.getIM());
        holder.tx5.setText(" "+product.getEM());
        holder.tx6.setText(" "+product.getTotal());
        holder.tx6.setText(" "+product.getTotal());
        holder.tx7.setText(product.getGrade());
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
          //  tx1 = itemView.findViewById(R.id.tx1);
            tx2 = itemView.findViewById(R.id.tx2);
            tx3 = itemView.findViewById(R.id.tx3);
            tx4 = itemView.findViewById(R.id.tx4);
            tx5 = itemView.findViewById(R.id.tx5);
            tx6 = itemView.findViewById(R.id.tx6);
            tx7 = itemView.findViewById(R.id.tx7);

        }
    }
}