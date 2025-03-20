package com.example.foodorderapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.Activity.Domain.FoodDomain;
import com.example.foodorderapp.Activity.Helper.ManagementCart;
import com.example.foodorderapp.R;
import android.util.Log;
import android.widget.Toast;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomain object;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        // Nhận object từ Intent (đã sửa dùng Parcelable)
        object = getIntent().getParcelableExtra("object");

        // Kiểm tra object có null không
        if (object == null) {
            Log.e("ERROR", "object is null in getBundle()");
            Toast.makeText(this, "Lỗi: Không tìm thấy dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Load dữ liệu vào giao diện
        titleTxt.setText(object.getTitle() != null ? object.getTitle() : "N/A");
        feeTxt.setText(object.getFee() != null ? "$" + object.getFee() : "$0.00");
        descriptionTxt.setText(object.getDescription() != null ? object.getDescription() : "Không có mô tả");
        numberOrderTxt.setText(String.valueOf(numberOrder));

        // Lấy resource ID của ảnh từ tên file
        int drawableResourceId = getResources().getIdentifier(object.getPic(), "drawable", getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        // Xử lý sự kiện nút tăng giảm số lượng
        plusBtn.setOnClickListener(v -> {
            numberOrder += 1;
            numberOrderTxt.setText(String.valueOf(numberOrder));
        });

        minusBtn.setOnClickListener(view -> {
            if (numberOrder > 1) numberOrder -= 1;
            numberOrderTxt.setText(String.valueOf(numberOrder));
        });

        // Xử lý sự kiện thêm vào giỏ hàng
        addToCartBtn.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managementCart.insertFood(object);
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.description);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.picfood);
    }
}
