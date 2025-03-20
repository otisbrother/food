package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.Activity.Adaptor.CartListAdapter;
import com.example.foodorderapp.Activity.Helper.ManagementCart;
import com.example.foodorderapp.Activity.Interface.ChangeNumberItemListener;
import com.example.foodorderapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewList;
    private CartListAdapter adapter;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private ScrollView scrollView;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        calculateCart();
        setupBottomNavigation();
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.cartRecyclerView); // Kiểm tra lại ID đúng với XML
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView4);

        if (recyclerViewList == null) {
            Log.e("CartListActivity", "RecyclerView is NULL! Check your XML file.");
        }
    }

    private void initList() {
        if (recyclerViewList == null) return; // Tránh lỗi nếu RecyclerView chưa được tìm thấy

        recyclerViewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (managementCart.getListCart() == null) {
            Log.e("CartListActivity", "Cart list is NULL!");
            return;
        }

        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                calculateCart();
                checkCartEmpty(); // Kiểm tra nếu giỏ hàng trống
            }
        });

        recyclerViewList.setAdapter(adapter);
        checkCartEmpty();
    }

    private void checkCartEmpty() {
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            recyclerViewList.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            recyclerViewList.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        double totalItemFee = managementCart.getTotalFee();

        if (totalItemFee == 0) {
            totalFeeTxt.setText("VND 0");
            taxTxt.setText("VND 0");
            deliveryTxt.setText("VND 0");
            totalTxt.setText("VND 0");
            return;
        }

        tax = Math.round((totalItemFee * percentTax) * 100) / 100.0;
        double total = Math.round((totalItemFee + tax + delivery) * 100) / 100.0;

        totalFeeTxt.setText(String.format("VND %.2f", totalItemFee));
        taxTxt.setText(String.format("VND %.2f", tax));
        deliveryTxt.setText(String.format("VND %.2f", delivery));
        totalTxt.setText(String.format("VND %.2f", total));
    }

    private void setupBottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        // Tránh lỗi khi bấm vào giỏ hàng mà mở lại chính Activity này
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CartListActivity", "Cart button clicked.");
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
                finish(); // Đóng Activity hiện tại để tránh quay lại giỏ hàng khi nhấn back
            }
        });
    }
}
