package uk.ac.tees.aad.obesity2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.ac.tees.aad.obesity2.R;
import  uk.ac.tees.aad.obesity2.Model.food;

public class foodadapter extends RecyclerView.Adapter<foodadapter.viewholder>{


    ArrayList<food> foods;
    Context context;

    public foodadapter(ArrayList<food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodrecommenderlayout,parent,false);
        return new foodadapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {



        Glide.with(context).load(foods.get(position).getFoodImage()).into(holder.FoodImage);
        holder.FoodLabel.setText(foods.get(position).getFoodtitle());
        holder.Foodcalories.setText(foods.get(position).getFoodcalories());
        holder.info1.setText(foods.get(position).getInfo1());
        holder.info2.setText(foods.get(position).getInfo2());
        holder.info3.setText(foods.get(position).getInfo3());
        holder.info4.setText(foods.get(position).getInfo4());
        holder.info5.setText(foods.get(position).getInfo5());
        holder.info6.setText(foods.get(position).getInfo6());
        holder.info7.setText(foods.get(position).getInfo7());
        holder.info8.setText(foods.get(position).getInfo8());
        holder.info9.setText(foods.get(position).getInfo9());
        holder.info10.setText(foods.get(position).getInfo10());
        holder.info11.setText(foods.get(position).getInfo11());
        holder.info12.setText(foods.get(position).getInfo12());
        holder.cousine.setText(foods.get(position).getCounsine());
        holder.fat.setText(foods.get(position).getFat());
        holder.protien.setText(foods.get(position).getProtine());
        holder.carb.setText(foods.get(position).getCarb());
        holder.cholestrol.setText(foods.get(position).getCholestrol());
        holder.calcium.setText(foods.get(position).getCalcium());
        holder.magniesum.setText(foods.get(position).getMagnisium());
        holder.sodium.setText(foods.get(position).getSodium());
        holder.iron.setText(foods.get(position).getIrom());




    }

    @Override
    public int getItemCount() {
       return  foods.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {


            ImageView FoodImage;
            TextView FoodLabel;
            TextView Foodcalories;
            TextView info1,info2,info3,info4,info5,info6,info7,info8,info9,info10,info11,info12;
            TextView cousine;
            TextView fat,protien,carb;
            TextView cholestrol,calcium, magniesum,sodium,iron;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            FoodImage = itemView.findViewById(R.id.foodimage);
            FoodLabel = itemView.findViewById(R.id.foodlabel);
            Foodcalories = itemView.findViewById(R.id.foodcalories);
            cousine = itemView.findViewById(R.id.cousine);
            fat = itemView.findViewById(R.id.fat);
            protien = itemView.findViewById(R.id.protien);
            carb = itemView.findViewById(R.id.carb);
            cholestrol = itemView.findViewById(R.id.cholestrol);
            calcium = itemView.findViewById(R.id.calcium);
            magniesum = itemView.findViewById(R.id.magnisium);
            sodium = itemView.findViewById(R.id.sodium);
            iron = itemView.findViewById(R.id.iron);
            info1 = itemView.findViewById(R.id.info1);
            info2 = itemView.findViewById(R.id.info2);
            info3 = itemView.findViewById(R.id.info3);
            info4 = itemView.findViewById(R.id.info4);
            info5 = itemView.findViewById(R.id.info5);
            info6 = itemView.findViewById(R.id.info6);
            info7 = itemView.findViewById(R.id.info7);
            info8 = itemView.findViewById(R.id.info8);
            info9 = itemView.findViewById(R.id.info9);
            info10 = itemView.findViewById(R.id.info10);
            info11 = itemView.findViewById(R.id.info11);
            info12 = itemView.findViewById(R.id.info12);


        }
    }
}
