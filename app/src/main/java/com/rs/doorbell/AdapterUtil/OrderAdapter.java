package com.rs.doorbell.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.CustomUtil.GlideApp;
import com.rs.doorbell.InterfaceUtil.ConnectionCallback;
import com.rs.doorbell.InterfaceUtil.OrderCallback;
import com.rs.doorbell.ManagementUtil.Management;
import com.rs.doorbell.ObjectUtil.DataObject;
import com.rs.doorbell.ObjectUtil.EmptyObject;
import com.rs.doorbell.ObjectUtil.PrefObject;
import com.rs.doorbell.ObjectUtil.ProgressObject;
import com.rs.doorbell.ObjectUtil.RequestObject;
import com.rs.doorbell.R;
import com.rs.doorbell.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter implements ConnectionCallback {
    private boolean isProfile;
    private int NO_DATA_VIEW = 1;
    private int ORDER_CURRENT_VIEW = 2;
    private int ORDER_HISTORY_VIEW = 3;
    private int PROGRESS_VIEW = 4;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private OrderCallback orderCallback;
    private Management management;
    private PrefObject prefObject;
    OrderHolder orderHolder;
    int positionss = 0;
    public OrderAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        management = new Management(context);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));
    }

    public OrderAdapter(Context context, ArrayList<Object> dataArray, OrderCallback orderCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.orderCallback = orderCallback;
        management = new Management(context);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));
    }


    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.CURRENT_ORDER) {
                return ORDER_CURRENT_VIEW;
            } else if (dataObject.getDataType() == Constant.DATA_TYPE.HISTORY_ORDER) {
                return ORDER_HISTORY_VIEW;
            }

        } else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == ORDER_CURRENT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_current_item_layout, parent, false);
            viewHolder = new OrderHolder(view,true);

        } else if (viewType == ORDER_HISTORY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_current_item_layout, parent, false);
            viewHolder = new OrderHolder(view,false);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            //LookUpObject lookUpObject = (LookUpObject) dataArray.get(position);
            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        } else if (holder instanceof OrderHolder) {


            orderHolder = (OrderHolder) holder;
            final DataObject dataObject = (DataObject) dataArray.get(position);

            orderHolder.txtName.setText(dataObject.getCustomer_name());
            orderHolder.txtDate.setText(dataObject.getDate());
            orderHolder.txtTime.setText(dataObject.getTime());
            orderHolder.txtCharges.setText(dataObject.getOrder_price());
            orderHolder.txtDeliveryTime.setText(dataObject.getOrder_delivery_time());

            orderHolder.txtRestaurantName.setText(dataObject.getRestaurant_name());
            orderHolder.txtAddress.setText(dataObject.getRestaurant_address());


            if (dataObject.getPaymentType().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.cod))) {
                orderHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.cash_on_delivery));
            } else if (dataObject.getPaymentType().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.debit_credit_card))) {
                orderHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.credit_debit_card));
            }

            orderHolder.layoutOrder.setTag(position);
            orderHolder.layoutOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) orderHolder.layoutOrder.getTag();
                    if (orderCallback != null) {
                        orderCallback.onOrderClickListener(pos);
                    }
                }
            });


            String pictureUrl = null;

            if (dataObject.getCustomer_picture().contains("google")){
                pictureUrl = dataObject.getCustomer_picture();
            }else if (dataObject.getCustomer_picture().contains("facebook")){
                pictureUrl = dataObject.getCustomer_picture() + Constant.ServerInformation.FACEBOOK_HIGH_PIXEL_URL;
            }else {
                pictureUrl = Constant.ServerInformation.PICTURE_URL + dataObject.getCustomer_picture();
            }


            GlideApp.with(context).load(pictureUrl).into(orderHolder.imageLogo);

            if (!orderHolder.isCurrentOrders)
                orderHolder.layoutAcceptOrder.setVisibility(View.GONE);

            orderHolder.layoutAcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionss = position;
                    if (orderCallback != null) {
                        sendRequestToServer(dataObject.getUser_id(),dataObject.getOrder_id());
                    }
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (orderCallback != null) {
            orderCallback.onOrderAcceptListener(positionss);
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;
        private LinearLayout layoutContainer;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            ///layoutContainer = view.findViewById(R.id.layout_container);
        }
    }

    protected class OrderHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutOrder;
        private RoundedImageView imageLogo;
        private TextView txtName;
        private TextView txtDate;
        private TextView txtTime;
        private TextView txtRating;
        private TextView txtDeliveryTime;
        private TextView txtCharges;
        private TextView txtStatus;
        private ImageView imageFavourite;
        private TextView layoutAcceptOrder;
        private TextView txtRestaurantName;
        private TextView txtAddress;
        private boolean isCurrentOrders;

        public OrderHolder(View view,boolean isCurrentOrders) {
            super(view);

            this.isCurrentOrders=isCurrentOrders;
            layoutOrder = (LinearLayout) view.findViewById(R.id.layout_order);
            imageLogo = (RoundedImageView) view.findViewById(R.id.image_logo);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtRating = (TextView) view.findViewById(R.id.txt_rating);
            txtDeliveryTime = (TextView) view.findViewById(R.id.txt_delivery_time);
            txtCharges = (TextView) view.findViewById(R.id.txt_charges);
            txtStatus = (TextView) view.findViewById(R.id.txt_status);
            imageFavourite = (ImageView) view.findViewById(R.id.image_favourite);
            layoutAcceptOrder = view.findViewById(R.id.layout_accept_order);
            txtRestaurantName = view.findViewById(R.id.txtRestaurantName);
            txtAddress = view.findViewById(R.id.txtAddress);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    private void sendRequestToServer(String user_id,String order_id) {

        management.sendRequestToServer(new RequestObject()
                .setContext(context)
                .setJson(getJson(prefObject.getUserId(),user_id,order_id))
                .setConnection(Constant.CONNECTION.sendRiderAccpetNotification)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnectionCallback(this));


    }

    private String getJson(String rider_id,String user_id,String order_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "sendRiderAccpetNotification");
            jsonObject.accumulate("rider_id", rider_id);
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("order_id", order_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("TAG", "JSON " + json);

        return json;
    }

}
