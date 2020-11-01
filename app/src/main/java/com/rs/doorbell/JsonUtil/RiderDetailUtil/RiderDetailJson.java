
package com.rs.doorbell.JsonUtil.RiderDetailUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiderDetailJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("riderDetail")
    @Expose
    private List<RiderDetail> riderDetail = null;
    public final static Parcelable.Creator<RiderDetailJson> CREATOR = new Creator<RiderDetailJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RiderDetailJson createFromParcel(Parcel in) {
            return new RiderDetailJson(in);
        }

        public RiderDetailJson[] newArray(int size) {
            return (new RiderDetailJson[size]);
        }

    }
    ;

    protected RiderDetailJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.riderDetail, (com.rs.doorbell.JsonUtil.RiderDetailUtil.RiderDetail.class.getClassLoader()));
    }

    public RiderDetailJson() {
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

    public List<RiderDetail> getRiderDetail() {
        return riderDetail;
    }

    public void setRiderDetail(List<RiderDetail> riderDetail) {
        this.riderDetail = riderDetail;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(riderDetail);
    }

    public int describeContents() {
        return  0;
    }

}
