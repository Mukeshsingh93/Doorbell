package com.rs.doorbell.InterfaceUtil;

public interface PlayerCallback {

    void onPlayerStarted(int audioSessionId);

    void onStreamChange(int position,long duration);

}
