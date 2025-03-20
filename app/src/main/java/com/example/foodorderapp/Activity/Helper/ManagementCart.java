package com.example.foodorderapp.Activity.Helper;

import android.content.Context;
import android.widget.Toast;
import com.example.foodorderapp.Activity.Domain.FoodDomain;
import com.example.foodorderapp.Activity.Interface.ChangeNumberItemListener;
import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        resetCart(); // Reset giỏ hàng về trống khi mở app
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();
        boolean exitAlready = false;
        int n = 0;

        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                exitAlready = true;
                n = i;
                break;
            }
        }

        if (exitAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CartList", listFood);
        Toast.makeText(context, "Đã thêm giỏ hàng!", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getListCart() {
        ArrayList<FoodDomain> list = tinyDB.getListObject("CartList");
        if (list == null) return new ArrayList<>();
        return list;
    }

    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemListener.changed();
    }

    public void minusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {
        if (listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemListener.changed();
    }

    public double getTotalFee() {
        ArrayList<FoodDomain> listFood = getListCart();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++) {
            fee += (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
        }
        return fee;
    }

    // Reset giỏ hàng về trống mỗi khi mở app
    public void resetCart() {
        tinyDB.remove("CartList");
    }
}
