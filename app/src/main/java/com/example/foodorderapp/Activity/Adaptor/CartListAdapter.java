package com.example.foodorderapp.Activity.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Activity.Domain.FoodDomain;
import com.example.foodorderapp.Activity.Helper.ManagementCart;
import com.example.foodorderapp.Activity.Interface.ChangeNumberItemListener;
import com.example.foodorderapp.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<FoodDomain> foodDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemListener changeNumberItemListener;
    private Context context;

    public CartListAdapter(ArrayList<FoodDomain> foodDomains, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.foodDomains = foodDomains;
        this.context = context;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomain foodItem = foodDomains.get(position);
        holder.title.setText(foodItem.getTitle());
        holder.feeEachItem.setText(String.valueOf(foodItem.getFee()));
        holder.totalEachItem.setText("VND " + Math.round(foodItem.getNumberInCart() * foodItem.getFee() * 100.0) / 100.0);
        holder.num.setText(String.valueOf(foodItem.getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(foodItem.getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        holder.plusItem.setOnClickListener(v -> {
            managementCart.plusNumberFood(foodDomains, position, () -> {
                notifyDataSetChanged();
                changeNumberItemListener.changed();
            });
        });

        holder.minusItem.setOnClickListener(v -> {
            managementCart.minusNumberFood(foodDomains, position, () -> {
                notifyDataSetChanged();
                changeNumberItemListener.changed();
            });
        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, totalEachItem, num;
        ImageView pic, plusItem, minusItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            pic = itemView.findViewById(R.id.picCart);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
