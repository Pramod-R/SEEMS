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

//import  com.bagicode.www.bagilogindesign.Student_Login_Activity.mStudentAttendProductList;


public class Student_Event_Product_Adapetr extends RecyclerView.Adapter<Student_Event_Product_Adapetr.ProductViewHolder> {

    private Context mCtx;
    private List<Event_Product> productList;

    public Student_Event_Product_Adapetr(Context mCtx, List<Event_Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.event_product_list, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Event_Product event_product= productList.get(position);
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
      //  holder.tx2.setText(event_product.getSemester());
      //  holder.tx3.setText(event_product.getSection());
      //  holder.tx4.setText(event_product.getBranch());
        holder.tx5.setText(event_product.getEventdate());
        holder.tx6.setText(event_product.getEventdesc());

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
          //  tx2 = itemView.findViewById(R.id.tex2);
          //  tx3 = itemView.findViewById(R.id.tex3);
          //  tx4 = itemView.findViewById(R.id.tex4);
            tx5 = itemView.findViewById(R.id.tex5);
            tx6 = itemView.findViewById(R.id.tex6);

            // tx1 = itemView.findViewById(R.id.tx1);
        }
    }
}