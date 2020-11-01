
package com.rs.doorbell.JsonUtil.RiderOrderUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RiderOrderHistoryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderList")
    @Expose
    private List<RiderOrderHistory> orderList = null;

    public RiderOrderHistoryJson() {
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

    public List<RiderOrderHistory> getOrderList() {
        return orderList;
    }

    public RiderOrderHistoryJson setOrderList(List<RiderOrderHistory> orderList) {
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

    protected RiderOrderHistoryJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.orderList = in.createTypedArrayList(RiderOrderHistory.CREATOR);
    }

    public static final Creator<RiderOrderHistoryJson> CREATOR = new Creator<RiderOrderHistoryJson>() {
        @Override
        public RiderOrderHistoryJson createFromParcel(Parcel source) {
            return new RiderOrderHistoryJson(source);
        }

        @Override
        public RiderOrderHistoryJson[] newArray(int size) {
            return new RiderOrderHistoryJson[size];
        }
    };
}
