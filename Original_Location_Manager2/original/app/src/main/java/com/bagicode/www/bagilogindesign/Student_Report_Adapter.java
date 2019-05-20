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


public class Student_Report_Adapter extends RecyclerView.Adapter<Student_Report_Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<Attendance_Info> productList;
    public Student_Report_Adapter(Context mCtx, List<Attendance_Info> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.student_report_list, null);
        return new ProductViewHolder(view);
    }

    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Attendance_Info product= productList.get(position);
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
        holder.tx1.setText(product.getSubCode());
        holder.tx2.setText(" "+product.getClass_held());
        holder.tx3.setText(" "+product.getClass_attended());
        holder.tx4.setText(" "+product.getPerc());

    }

    public int getItemCount() {
        return productList.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tx2, tx3, tx4, tx5, tx6, tx1,tx7;
        ImageView imageView;
        public ProductViewHolder(View itemView) {
            super(itemView);
            //  tx1 = itemView.findViewById(R.id.tx1);
            tx1 = itemView.findViewById(R.id.tx1);
            tx2 = itemView.findViewById(R.id.tx2);
            tx3 = itemView.findViewById(R.id.tx3);
            tx4 = itemView.findViewById(R.id.tx4);

        }
    }
}
