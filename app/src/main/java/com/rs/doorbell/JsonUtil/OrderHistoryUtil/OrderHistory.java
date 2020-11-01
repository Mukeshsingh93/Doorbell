
package com.rs.doorbell.JsonUtil.OrderHistoryUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_logo")
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    public OrderHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public OrderHistory setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.restaurantLogo);
        dest.writeString(this.restaurantName);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.orderPrice);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.paymentType);
        dest.writeString(this.orderStatus);
    }

    protected OrderHistory(Parcel in) {
        this.id = in.readString();
        this.restaurantLogo = in.readString();
        this.restaurantName = in.readString();
        this.deliveryDate = in.readString();
        this.orderPrice = in.readString();
        this.deliveryTime = in.readString();
        this.paymentType = in.readString();
        this.orderStatus = in.readString();
    }

    public static final Creator<OrderHistory> CREATOR = new Creator<OrderHistory>() {
        @Override
        public OrderHistory createFromParcel(Parcel source) {
            return new OrderHistory(source);
        }

        @Override
        public OrderHistory[] newArray(int size) {
            return new OrderHistory[size];
        }
    };
}
