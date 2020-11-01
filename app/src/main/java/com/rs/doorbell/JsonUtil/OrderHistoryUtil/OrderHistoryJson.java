
package com.rs.doorbell.JsonUtil.OrderHistoryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderList")
    @Expose
    private List<OrderHistory> orderList = null;

    public OrderHistoryJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderHistory> getOrderList() {
        return orderList;
    }

    public OrderHistoryJson setOrderList(List<OrderHistory> orderList) {
        this.orderList = orderList;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.orderList);
    }

    protected OrderHistoryJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.orderList = in.createTypedArrayList(OrderHistory.CREATOR);
    }

    public static final Creator<OrderHistoryJson> CREATOR = new Creator<OrderHistoryJson>() {
        @Override
        public OrderHistoryJson createFromParcel(Parcel source) {
            return new OrderHistoryJson(source);
        }

        @Override
        public OrderHistoryJson[] newArray(int size) {
            return new OrderHistoryJson[size];
        }
    };
}
