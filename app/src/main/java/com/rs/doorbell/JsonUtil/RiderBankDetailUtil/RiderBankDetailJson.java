
package com.rs.doorbell.JsonUtil.RiderBankDetailUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiderBankDetailJson implements Parcelable
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
    public final static Parcelable.Creator<RiderBankDetailJson> CREATOR = new Creator<RiderBankDetailJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RiderBankDetailJson createFromParcel(Parcel in) {
            return new RiderBankDetailJson(in);
        }

        public RiderBankDetailJson[] newArray(int size) {
            return (new RiderBankDetailJson[size]);
        }

    }
    ;

    protected RiderBankDetailJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.riderDetail, (com.rs.doorbell.JsonUtil.RiderBankDetailUtil.RiderDetail.class.getClassLoader()));
    }

    public RiderBankDetailJson() {
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
