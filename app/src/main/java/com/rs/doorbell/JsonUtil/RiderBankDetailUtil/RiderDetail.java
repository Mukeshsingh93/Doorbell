
package com.rs.doorbell.JsonUtil.RiderBankDetailUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiderDetail implements Parcelable
{

    @SerializedName("account_holder_name_id")
    @Expose
    private String accountHolderNameId;
    @SerializedName("holder_name")
    @Expose
    private String holderName;
    @SerializedName("account_number_id")
    @Expose
    private String accountNumberId;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("bank_number_id")
    @Expose
    private String bankNumberId;
    @SerializedName("bank_no")
    @Expose
    private String bankNo;
    @SerializedName("bank_transit_number_id")
    @Expose
    private String bankTransitNumberId;
    @SerializedName("bank_transit_no")
    @Expose
    private String bankTransitNo;
    public final static Parcelable.Creator<RiderDetail> CREATOR = new Creator<RiderDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RiderDetail createFromParcel(Parcel in) {
            return new RiderDetail(in);
        }

        public RiderDetail[] newArray(int size) {
            return (new RiderDetail[size]);
        }

    }
    ;

    protected RiderDetail(Parcel in) {
        this.accountHolderNameId = ((String) in.readValue((String.class.getClassLoader())));
        this.holderName = ((String) in.readValue((String.class.getClassLoader())));
        this.accountNumberId = ((String) in.readValue((String.class.getClassLoader())));
        this.accountNo = ((String) in.readValue((String.class.getClassLoader())));
        this.bankNumberId = ((String) in.readValue((String.class.getClassLoader())));
        this.bankNo = ((String) in.readValue((String.class.getClassLoader())));
        this.bankTransitNumberId = ((String) in.readValue((String.class.getClassLoader())));
        this.bankTransitNo = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RiderDetail() {
    }

    public String getAccountHolderNameId() {
        return accountHolderNameId;
    }

    public void setAccountHolderNameId(String accountHolderNameId) {
        this.accountHolderNameId = accountHolderNameId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountNumberId() {
        return accountNumberId;
    }

    public void setAccountNumberId(String accountNumberId) {
        this.accountNumberId = accountNumberId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankNumberId() {
        return bankNumberId;
    }

    public void setBankNumberId(String bankNumberId) {
        this.bankNumberId = bankNumberId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankTransitNumberId() {
        return bankTransitNumberId;
    }

    public void setBankTransitNumberId(String bankTransitNumberId) {
        this.bankTransitNumberId = bankTransitNumberId;
    }

    public String getBankTransitNo() {
        return bankTransitNo;
    }

    public void setBankTransitNo(String bankTransitNo) {
        this.bankTransitNo = bankTransitNo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(accountHolderNameId);
        dest.writeValue(holderName);
        dest.writeValue(accountNumberId);
        dest.writeValue(accountNo);
        dest.writeValue(bankNumberId);
        dest.writeValue(bankNo);
        dest.writeValue(bankTransitNumberId);
        dest.writeValue(bankTransitNo);
    }

    public int describeContents() {
        return  0;
    }

}
