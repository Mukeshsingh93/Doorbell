
package com.rs.doorbell.JsonUtil.RiderDetailUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiderDetail implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rider_documents")
    @Expose
    private String riderDocuments;
    @SerializedName("document_status")
    @Expose
    private String documentStatus;

    public RiderDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiderDocuments() {
        return riderDocuments;
    }

    public void setRiderDocuments(String riderDocuments) {
        this.riderDocuments = riderDocuments;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public RiderDetail setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.riderDocuments);
        dest.writeString(this.documentStatus);
    }

    protected RiderDetail(Parcel in) {
        this.id = in.readString();
        this.riderDocuments = in.readString();
        this.documentStatus = in.readString();
    }

    public static final Creator<RiderDetail> CREATOR = new Creator<RiderDetail>() {
        @Override
        public RiderDetail createFromParcel(Parcel source) {
            return new RiderDetail(source);
        }

        @Override
        public RiderDetail[] newArray(int size) {
            return new RiderDetail[size];
        }
    };
}
