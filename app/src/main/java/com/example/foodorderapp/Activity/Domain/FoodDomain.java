package com.example.foodorderapp.Activity.Domain;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodDomain implements Parcelable {
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private int numberInCart;

    // Constructor đầy đủ
    public FoodDomain(String title, String pic, String description, Double fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }

    // Constructor không cần số lượng trong giỏ hàng
    public FoodDomain(String title, String pic, String description, Double fee) {
        this(title, pic, description, fee, 0);
    }

    // Constructor khi đọc từ Parcel
    protected FoodDomain(Parcel in) {
        title = in.readString();
        pic = in.readString();
        description = in.readString();
        fee = in.readByte() == 0 ? null : in.readDouble();
        numberInCart = in.readInt();
    }

    // Parcelable CREATOR
    public static final Creator<FoodDomain> CREATOR = new Creator<FoodDomain>() {
        @Override
        public FoodDomain createFromParcel(Parcel in) {
            return new FoodDomain(in);
        }

        @Override
        public FoodDomain[] newArray(int size) {
            return new FoodDomain[size];
        }
    };

    // Ghi dữ liệu vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pic);
        dest.writeString(description);
        if (fee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(fee);
        }
        dest.writeInt(numberInCart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
