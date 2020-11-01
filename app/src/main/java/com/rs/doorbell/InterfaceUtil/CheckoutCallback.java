package com.rs.doorbell.InterfaceUtil;

import com.rs.doorbell.ObjectUtil.AddressObject;
import com.rs.doorbell.ObjectUtil.BillingObject;
import com.rs.doorbell.ObjectUtil.ScheduleObject;

public interface CheckoutCallback {

    void onAddressChangeListener(AddressObject addressObject);

    void onBillingChangeListener(BillingObject billingObject);

    void onDeliveryChangeListener(ScheduleObject scheduleObject);

}
