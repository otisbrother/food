package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderapp.Activity.Adaptor.CategoryAdaptor;
import com.example.foodorderapp.Activity.Adaptor.PopularAdaptor;
import com.example.foodorderapp.Activity.Domain.CategoryDomain;
import com.example.foodorderapp.Activity.Domain.FoodDomain;
import com.example.foodorderapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerViews();
        bottomNavigation();
    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity .this,CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity .this,MainActivity.class));
            }
        });
    }
    private void setupRecyclerViews() {
        // Cấu hình danh sách Category
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerViewCategoryList, getCategoryList(), new CategoryAdaptor(getCategoryList()));

        // Cấu hình danh sách Popular
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        setupRecyclerView(recyclerViewPopularList, getFoodList(), new PopularAdaptor(getFoodList()));
    }

    private void setupRecyclerView(RecyclerView recyclerView, ArrayList<?> list, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacingItemDecoration(16)); // Thêm khoảng cách 16px giữa các item
    }

    private ArrayList<CategoryDomain> getCategoryList() {
        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_2"));
        category.add(new CategoryDomain("Burger", "cat_2"));
        category.add(new CategoryDomain("Hotdog", "cat_3"));
        category.add(new CategoryDomain("Drink", "cat_4"));
        category.add(new CategoryDomain("Donut", "cat_5"));
        return category;
    }

    private ArrayList<FoodDomain> getFoodList() {
        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("pizza", "pizza", "\n" +"lát pepperoni, phô mai mozzarella, rau oregano tươi, hạt tiêu đen xay, sốt pizza", 5.76));
        foodList.add(new FoodDomain("burger", "pop_2", "thịt bò, phô mai Gouda, nước sốt đặc biệt, rau diếp, cà chua", 7.56));
        foodList.add(new FoodDomain("pizza2", "pop_3", "\n" +  "dầu ô liu, dầu thực vật, cà chua bi bỏ hạt, sốt pizza", 8.76));
        return foodList;
    }
}
