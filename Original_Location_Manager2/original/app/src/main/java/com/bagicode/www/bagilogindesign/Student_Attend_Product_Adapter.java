package com.sapthagiri.www.sap;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import  com.bagicode.www.bagilogindesign.Student_Login_Activity.mStudentAttendProductList;


public class Student_Attend_Product_Adapter extends RecyclerView.Adapter<Student_Attend_Product_Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<Student_Attend_Product> productList;

    public Student_Attend_Product_Adapter(Context mCtx, List<Student_Attend_Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.student_attend_product_list, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Student_Attend_Product mStudentAttendProductList= productList.get(position);
        //loading the image
       // ArrayList<Student_Attend_Product>  mStudentAttendProductList = new ArrayList<>();

        // Student_Attend_Product mStudentAttendProductList=null;
        //holder.tx1.setText(" "+mStudentAttendProductList.getAid());
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
        holder.tx2.setText(mStudentAttendProductList.getCoursecode());
        holder.tx3.setText(mStudentAttendProductList.getCoursename());
        holder.tx4.setText(" "+mStudentAttendProductList.getClassconduct());
        holder.tx5.setText(" "+mStudentAttendProductList.getClassAttend());
        holder.tx6.setText(" "+mStudentAttendProductList.getPercentage());
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tx2, tx3, tx4, tx5, tx6, tx1;
        ImageView imageView;
        public ProductViewHolder(View itemView) {
            super(itemView);
           // tx1 = itemView.findViewById(R.id.tx1);
            tx2 = itemView.findViewById(R.id.tx2);
            tx3 = itemView.findViewById(R.id.tx3);
            tx4 = itemView.findViewById(R.id.tx4);
            tx5 = itemView.findViewById(R.id.tx5);
            tx6 = itemView.findViewById(R.id.tx6);
        }
    }
}