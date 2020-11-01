
package com.rs.doorbell.JsonUtil.RiderOrderUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiderOrderHistory implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rider_order_id")
    @Expose
    private String rider_order_id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("user_picture")
    @Expose
    private String user_picture;
    @SerializedName("restaurant_logo")
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
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

    @SerializedName("billing_address")
    @Expose
    private String billing_address;
    @SerializedName("building_no")
    @Expose
    private String building_no;
    @SerializedName("area_name")
    @Expose
    private String area_name;
    @SerializedName("floor_name")
    @Expose
    private String floor_name;
    @SerializedName("rider_note")
    @Expose
    private String rider_note;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("user_lattitude")
    @Expose
    private String user_lattitude;

    @SerializedName("user_longitude")
    @Expose
    private String user_longitude;

    @SerializedName("restaurant_address")
    @Expose
    private String restaurant_address;

    @SerializedName("phoneno")
    @Expose
    private String phoneno;


    public RiderOrderHistory() {
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

    public RiderOrderHistory setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
        return this;
    }

    public String getRider_order_id() {
        return rider_order_id;
    }

    public RiderOrderHistory setRider_order_id(String rider_order_id) {
        this.rider_order_id = rider_order_id;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public RiderOrderHistory setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public RiderOrderHistory setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public RiderOrderHistory setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public RiderOrderHistory setUser_picture(String user_picture) {
        this.user_picture = user_picture;
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

    public String getBilling_address() {
        return billing_address;
    }

    public RiderOrderHistory setBilling_address(String billing_address) {
        this.billing_address = billing_address;
        return this;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public RiderOrderHistory setBuilding_no(String building_no) {
        this.building_no = building_no;
        return this;
    }

    public String getArea_name() {
        return area_name;
    }

    public RiderOrderHistory setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public RiderOrderHistory setFloor_name(String floor_name) {
        this.floor_name = floor_name;
        return this;
    }

    public String getRider_note() {
        return rider_note;
    }

    public RiderOrderHistory setRider_note(String rider_note) {
        this.rider_note = rider_note;
        return this;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public RiderOrderHistory setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
        return this;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public RiderOrderHistory setPhoneno(String phoneno) {
        this.phoneno = phoneno;
        return  this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.rider_order_id);
        dest.writeString(this.user_name);
        dest.writeString(this.user_picture);
        dest.writeString(this.restaurantLogo);
        dest.writeString(this.restaurantName);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.orderPrice);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.paymentType);
        dest.writeString(this.billing_address);
        dest.writeString(this.building_no);
        dest.writeString(this.area_name);
        dest.writeString(this.floor_name);
        dest.writeString(this.rider_note);
        dest.writeString(this.user_id);
        dest.writeString(this.user_lattitude);
        dest.writeString(this.user_longitude);
        dest.writeString(this.restaurant_address);
        dest.writeString(this.phoneno);
    }

    protected RiderOrderHistory(Parcel in) {
        this.id = in.readString();
        this.rider_order_id = in.readString();
        this.user_name = in.readString();
        this.user_picture = in.readString();
        this.restaurantLogo = in.readString();
        this.restaurantName = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.deliveryDate = in.readString();
        this.orderPrice = in.readString();
        this.deliveryTime = in.readString();
        this.paymentType = in.readString();
        this.billing_address = in.readString();
        this.building_no = in.readString();
        this.area_name = in.readString();
        this.floor_name = in.readString();
        this.rider_note = in.readString();
        this.user_id = in.readString();
        this.user_lattitude = in.readString();
        this.user_longitude = in.readString();
        this.restaurant_address = in.readString();
        this.phoneno = in.readString();
    }

    public static final Creator<RiderOrderHistory> CREATOR = new Creator<RiderOrderHistory>() {
        @Override
        public RiderOrderHistory createFromParcel(Parcel source) {
            return new RiderOrderHistory(source);
        }

        @Override
        public RiderOrderHistory[] newArray(int size) {
            return new RiderOrderHistory[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_lattitude() {
        return user_lattitude;
    }

    public void setUser_lattitude(String user_lattitude) {
        this.user_lattitude = user_lattitude;
    }

    public String getUser_longitude() {
        return user_longitude;
    }

    public void setUser_longitude(String user_longitude) {
        this.user_longitude = user_longitude;
    }
}
