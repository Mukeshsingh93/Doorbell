package com.rs.doorbell.ObjectUtil;

public class PictureObject {
    private String id;
    private String picture;
    private String status;
    private boolean isBase64 = true;
    private boolean isLongTap;
    private boolean isNewlyAdded = false;


    public boolean isNewlyAdded() {
        return isNewlyAdded;
    }

    public PictureObject setNewlyAdded(boolean newlyAdded) {
        isNewlyAdded = newlyAdded;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PictureObject setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getId() {
        return id;
    }

    public PictureObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public PictureObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public boolean isBase64() {
        return isBase64;
    }

    public PictureObject setBase64(boolean base64) {
        isBase64 = base64;
        return this;
    }

    public boolean isLongTap() {
        return isLongTap;
    }

    public PictureObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }
}
