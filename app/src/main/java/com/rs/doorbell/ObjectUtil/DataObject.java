package com.rs.doorbell.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.JsonUtil.RiderBankDetailUtil.RiderBankDetailJson;
import com.rs.doorbell.JsonUtil.RiderDetailUtil.RiderDetail;
import com.rs.doorbell.JsonUtil.RiderDetailUtil.RiderDetailJson;
import com.rs.doorbell.JsonUtil.RiderOrderUtil.RiderOrderHistory;
import com.rs.doorbell.JsonUtil.RiderOrderUtil.RiderOrderHistoryJson;
import com.rs.doorbell.JsonUtil.UserUtil.FavouriteList;
import com.rs.doorbell.JsonUtil.UserUtil.UserJson;
import com.rs.doorbell.Utility.Utility;

import java.util.ArrayList;

public class DataObject implements Parcelable {

    /* Variable for Connection Builder */

    private String code;
    private String message;

    /* Variable for Debugging */

    private static String TAG = DataObject.class.getName();

    /* Variable for UserObject */

    private String user_id;
    private String user_fName;
    private String user_lName;
    private String user_password;
    private String login_type;
    private String user_picture;
    private String user_phone;
    private String user_email;
    private String user_sign_in;
    private String user_remember;
    private ArrayList<FavouriteList> userFavourite = new ArrayList<>();
    private String user_payment_type;


    /* Variable for Restaurant Detail */

    private String object_id;
    private String object_name;
    private String object_Description;
    private String object_category_name;
    private String object_min_delivery_time;
    private String object_min_order_price;
    private String object_delivery_charges;
    private String object_expense;
    private String object_rating;
    private String object_no_of_rating;
    private String object_currency;
    private String object_currency_symbol;
    private String object_status;
    private String object_latitude;
    private String object_longitude;
    private String object_address;
    private String object_picture;
    private String object_logo;
    private String object_distance;
    private String object_tags;
    private String object_date_created;
    private String paymentType;
    private boolean is_layout_01 = true;


    /* Variable for Data Type */

    private Constant.DATA_TYPE dataType;
    private ArrayList<DataObject> objectArrayList = new ArrayList<>();
    private ArrayList<Object> homeList = new ArrayList<>();

    /* Variable for Favourites */

    private String favourite_id;
    private boolean isFavourite = false;
    private String cart_id;


    /* Variable for Chatting */

    private String chatting;
    private String picture;
    private String date;
    private String time;
    private boolean isFrom;

    /* General Variable  */

    private boolean isLongTap;
    private boolean isFirstItem;
    private boolean isAlreadyAddedIntoCart;
    private boolean isPaymentCardSelected;
    private String noOfItemInCart;
    private String basePrice;

    /* Variable for Rider */

    private String rider_id;
    private String customer_name;
    private String customer_picture;
    private String phoneno;

    private String rider_document_id;
    private String rider_document;
    private String document_status;

    private String account_holder_id;
    private String account_holder_name;
    private String account_no_id;
    private String account_no;
    private String bank_no_id;
    private String bank_no;
    private String bank_transist_id;
    private String bank_transist_no;


    /* Variable for Order History */

    private String order_id;
    private String order_restaurant_name;
    private String order_delivery_date;
    private String order_delivery_time;
    private String order_price;
    private String user_lattitude;
    private String user_longitude;
    private String delivery_time;
    private String payment_type;
    private String order_status;
    private double rider_latitude;
    private double rider_longitude;

    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_logo;


    private String buildingName;
    private String streetAddressName;
    private String areaName;
    private String unitName;
    private String riderNote;



    public String getCode() {
        return code;
    }

    public DataObject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DataObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRider_id() {
        return rider_id;
    }

    public DataObject setRider_id(String rider_id) {
        this.rider_id = rider_id;
        return this;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public DataObject setPhoneno(String phoneno) {
        this.phoneno = phoneno;
        return  this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public DataObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public DataObject setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
        return this;
    }

    public String getCustomer_picture() {
        return customer_picture;
    }

    public DataObject setCustomer_picture(String customer_picture) {
        this.customer_picture = customer_picture;
        return this;
    }

    public String getRider_document_id() {
        return rider_document_id;
    }

    public DataObject setRider_document_id(String rider_document_id) {
        this.rider_document_id = rider_document_id;
        return this;
    }

    public String getRider_document() {
        return rider_document;
    }

    public DataObject setRider_document(String rider_document) {
        this.rider_document = rider_document;
        return this;
    }

    public String getDocument_status() {
        return document_status;
    }

    public DataObject setDocument_status(String document_status) {
        this.document_status = document_status;
        return this;
    }

    public String getAccount_holder_id() {
        return account_holder_id;
    }

    public DataObject setAccount_holder_id(String account_holder_id) {
        this.account_holder_id = account_holder_id;
        return this;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public DataObject setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
        return this;
    }

    public String getAccount_no_id() {
        return account_no_id;
    }

    public DataObject setAccount_no_id(String account_no_id) {
        this.account_no_id = account_no_id;
        return this;
    }

    public String getAccount_no() {
        return account_no;
    }

    public DataObject setAccount_no(String account_no) {
        this.account_no = account_no;
        return this;
    }

    public String getBank_no_id() {
        return bank_no_id;
    }

    public DataObject setBank_no_id(String bank_no_id) {
        this.bank_no_id = bank_no_id;
        return this;
    }

    public String getBank_no() {
        return bank_no;
    }

    public DataObject setBank_no(String bank_no) {
        this.bank_no = bank_no;
        return this;
    }

    public String getBank_transist_id() {
        return bank_transist_id;
    }

    public DataObject setBank_transist_id(String bank_transist_id) {
        this.bank_transist_id = bank_transist_id;
        return this;
    }

    public String getBank_transist_no() {
        return bank_transist_no;
    }

    public DataObject setBank_transist_no(String bank_transist_no) {
        this.bank_transist_no = bank_transist_no;
        return this;
    }

    public String getOrder_restaurant_name() {
        return order_restaurant_name;
    }

    public DataObject setOrder_restaurant_name(String order_restaurant_name) {
        this.order_restaurant_name = order_restaurant_name;
        return this;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public DataObject setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
        return this;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public DataObject setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
        return this;
    }

    public String getRestaurant_logo() {
        return restaurant_logo;
    }

    public DataObject setRestaurant_logo(String restaurant_logo) {
        this.restaurant_logo = restaurant_logo;
        return this;
    }

    public String getOrder_delivery_date() {
        return order_delivery_date;
    }

    public DataObject setOrder_delivery_date(String order_delivery_date) {
        this.order_delivery_date = order_delivery_date;
        return this;
    }

    public String getOrder_delivery_time() {
        return order_delivery_time;
    }

    public DataObject setOrder_delivery_time(String order_delivery_time) {
        this.order_delivery_time = order_delivery_time;
        return this;
    }

    public String getOrder_price() {
        return order_price;
    }

    public DataObject setOrder_price(String order_price) {
        this.order_price = order_price;
        return this;
    }

    public String getUser_lattitude() {
        return user_lattitude;
    }

    public DataObject setUser_lattitude(String user_lattitude) {
        this.user_lattitude = user_lattitude;
        return this;
    }

    public String getUser_longitude() {
        return user_longitude;
    }

    public DataObject setUser_longitude(String user_longitude) {
        this.user_longitude = user_longitude;
        return this;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public DataObject setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
        return this;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public DataObject setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        return this;
    }

    public String getOrder_status() {
        return order_status;
    }

    public DataObject setOrder_status(String order_status) {
        this.order_status = order_status;
        return this;
    }

    public double getRider_latitude() {
        return rider_latitude;
    }

    public DataObject setRider_latitude(double rider_latitude) {
        this.rider_latitude = rider_latitude;
        return this;
    }

    public double getRider_longitude() {
        return rider_longitude;
    }

    public DataObject setRider_longitude(double rider_longitude) {
        this.rider_longitude = rider_longitude;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public DataObject setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public DataObject setUser_fName(String user_fName) {
        this.user_fName = user_fName;
        return this;
    }

    public String getUser_lName() {
        return user_lName;
    }

    public DataObject setUser_lName(String user_lName) {
        this.user_lName = user_lName;
        return this;
    }

    public String getUser_password() {
        return user_password;
    }

    public DataObject setUser_password(String user_password) {
        this.user_password = user_password;
        return this;
    }

    public String getLogin_type() {
        return login_type;
    }

    public DataObject setLogin_type(String login_type) {
        this.login_type = login_type;
        return this;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public DataObject setUser_picture(String user_picture) {
        this.user_picture = user_picture;
        return this;
    }

    public String getPhone() {
        return user_phone;
    }

    public DataObject setPhone(String phone) {
        this.user_phone = phone;
        return this;
    }

    public String getUser_email() {
        return user_email;
    }

    public DataObject setUser_email(String user_email) {
        this.user_email = user_email;
        return this;
    }

    public String getUser_sign_in() {
        return user_sign_in;
    }

    public DataObject setUser_sign_in(String user_sign_in) {
        this.user_sign_in = user_sign_in;
        return this;
    }

    public String getUser_remember() {
        return user_remember;
    }

    public DataObject setUser_remember(String user_remember) {
        this.user_remember = user_remember;
        return this;
    }

    public ArrayList<FavouriteList> getUserFavourite() {
        return userFavourite;
    }

    public DataObject setUserFavourite(ArrayList<FavouriteList> userFavourite) {
        this.userFavourite = userFavourite;
        return this;
    }

    public String getUser_payment_type() {
        return user_payment_type;
    }

    public DataObject setUser_payment_type(String user_payment_type) {
        this.user_payment_type = user_payment_type;
        return this;
    }


    public String getObject_id() {
        return object_id;
    }

    public DataObject setObject_id(String object_id) {
        this.object_id = object_id;
        return this;
    }

    public String getObject_name() {
        return object_name;
    }

    public DataObject setObject_name(String object_name) {
        this.object_name = object_name;
        return this;
    }

    public String getObject_Description() {
        return object_Description;
    }

    public DataObject setObject_Description(String object_Description) {
        this.object_Description = object_Description;
        return this;
    }

    public String getObject_category_name() {
        return object_category_name;
    }

    public DataObject setObject_category_name(String object_category_name) {
        this.object_category_name = object_category_name;
        return this;
    }

    public String getObject_min_delivery_time() {
        return object_min_delivery_time;
    }

    public DataObject setObject_min_delivery_time(String object_min_delivery_time) {
        this.object_min_delivery_time = object_min_delivery_time;
        return this;
    }

    public String getObject_min_order_price() {
        return object_min_order_price;
    }

    public DataObject setObject_min_order_price(String object_min_order_price) {
        this.object_min_order_price = object_min_order_price;
        return this;
    }

    public String getObject_delivery_charges() {
        return object_delivery_charges;
    }

    public DataObject setObject_delivery_charges(String object_delivery_charges) {
        this.object_delivery_charges = object_delivery_charges;
        return this;
    }

    public String getObject_expense() {
        return object_expense;
    }

    public DataObject setObject_expense(String object_expense) {
        this.object_expense = object_expense;
        return this;
    }

    public String getObject_rating() {
        return object_rating;
    }

    public DataObject setObject_rating(String object_rating) {
        this.object_rating = object_rating;
        return this;
    }

    public String getObject_no_of_rating() {
        return object_no_of_rating;
    }

    public DataObject setObject_no_of_rating(String object_no_of_rating) {
        this.object_no_of_rating = object_no_of_rating;
        return this;
    }

    public String getObject_currency() {
        return object_currency;
    }

    public DataObject setObject_currency(String object_currency) {
        this.object_currency = object_currency;
        return this;
    }

    public String getObject_currency_symbol() {
        return object_currency_symbol;
    }

    public DataObject setObject_currency_symbol(String object_currency_symbol) {
        this.object_currency_symbol = object_currency_symbol;
        return this;
    }

    public String getObject_status() {
        return object_status;
    }

    public DataObject setObject_status(String object_status) {
        this.object_status = object_status;
        return this;
    }

    public String getObject_latitude() {
        return object_latitude;
    }

    public DataObject setObject_latitude(String object_latitude) {
        this.object_latitude = object_latitude;
        return this;
    }

    public String getObject_longitude() {
        return object_longitude;
    }

    public DataObject setObject_longitude(String object_longitude) {
        this.object_longitude = object_longitude;
        return this;
    }

    public String getObject_address() {
        return object_address;
    }

    public DataObject setObject_address(String object_address) {
        this.object_address = object_address;
        return this;
    }

    public String getObject_picture() {
        return object_picture;
    }

    public DataObject setObject_picture(String object_picture) {
        this.object_picture = object_picture;
        return this;
    }

    public String getObject_logo() {
        return object_logo;
    }

    public DataObject setObject_logo(String object_logo) {
        this.object_logo = object_logo;
        return this;
    }

    public String getObject_distance() {
        return object_distance;
    }

    public DataObject setObject_distance(String object_distance) {
        this.object_distance = object_distance;
        return this;
    }

    public String getObject_tags() {
        return object_tags;
    }

    public DataObject setObject_tags(String object_tags) {
        this.object_tags = object_tags;
        return this;
    }

    public String getObject_date_created() {
        return object_date_created;
    }

    public DataObject setObject_date_created(String object_date_created) {
        this.object_date_created = object_date_created;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public DataObject setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }


    public Constant.DATA_TYPE getDataType() {
        return dataType;
    }

    public DataObject setDataType(Constant.DATA_TYPE dataType) {
        this.dataType = dataType;
        return this;
    }

    public boolean isLongTap() {
        return isLongTap;
    }

    public DataObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }

    public boolean isFirstItem() {
        return isFirstItem;
    }

    public DataObject setFirstItem(boolean firstItem) {
        isFirstItem = firstItem;
        return this;
    }

    public boolean isAlreadyAddedIntoCart() {
        return isAlreadyAddedIntoCart;
    }

    public DataObject setAlreadyAddedIntoCart(boolean alreadyAddedIntoCart) {
        isAlreadyAddedIntoCart = alreadyAddedIntoCart;
        return this;
    }

    public boolean isPaymentCardSelected() {
        return isPaymentCardSelected;
    }

    public DataObject setPaymentCardSelected(boolean paymentCardSelected) {
        isPaymentCardSelected = paymentCardSelected;
        return this;
    }

    public String getNoOfItemInCart() {
        return noOfItemInCart;
    }

    public DataObject setNoOfItemInCart(String noOfItemInCart) {
        this.noOfItemInCart = noOfItemInCart;
        return this;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public DataObject setBasePrice(String basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public ArrayList<DataObject> getObjectArrayList() {
        return objectArrayList;
    }

    public DataObject setObjectArrayList(ArrayList<DataObject> objectArrayList) {
        this.objectArrayList = objectArrayList;
        return this;
    }

    public ArrayList<Object> getHomeList() {
        return homeList;
    }

    public DataObject setHomeList(ArrayList<Object> homeList) {
        this.homeList = homeList;
        return this;
    }

    public String getFavourite_id() {
        return favourite_id;
    }

    public DataObject setFavourite_id(String favourite_id) {
        this.favourite_id = favourite_id;
        return this;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public DataObject setFavourite(boolean favourite) {
        isFavourite = favourite;
        return this;
    }

    public String getCart_id() {
        return cart_id;
    }

    public DataObject setCart_id(String cart_id) {
        this.cart_id = cart_id;
        return this;
    }

    public String getChatting() {
        return chatting;
    }

    public DataObject setChatting(String chatting) {
        this.chatting = chatting;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public DataObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DataObject setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public DataObject setTime(String time) {
        this.time = time;
        return this;
    }

    public boolean isFrom() {
        return isFrom;
    }

    public DataObject setFrom(boolean from) {
        isFrom = from;
        return this;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public DataObject setBuildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    public String getStreetAddressName() {
        return streetAddressName;
    }

    public DataObject setStreetAddressName(String streetAddressName) {
        this.streetAddressName = streetAddressName;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public DataObject setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getUnitName() {
        return unitName;
    }

    public DataObject setUnitName(String unitName) {
        this.unitName = unitName;
        return this;
    }

    public String getRiderNote() {
        return riderNote;
    }

    public DataObject setRiderNote(String riderNote) {
        this.riderNote = riderNote;
        return this;
    }

    public static DataObject getDataObject(RequestObject requestObject, Object data) {

        DataObject dataObject = null;
        String nextPage = null;

        if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
        || requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setUser_picture(userJson.getAvatar());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RIDER_CURRENT_ORDER
                || requestObject.getConnection() == Constant.CONNECTION.RIDER_HISTORY_ORDER) {

            //region Rider Current & History Order
            ArrayList<DataObject> restaurantList = new ArrayList<>();
            RiderOrderHistoryJson riderOrderHistoryJson = (RiderOrderHistoryJson) data;


            /*List of Order History*/

            for (int i = 0; i < riderOrderHistoryJson.getOrderList().size(); i++) {

                RiderOrderHistory featured = riderOrderHistoryJson.getOrderList().get(i);

                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setDatetimeType(Constant.DATETIME.BOTH12)
                        .setDatetime(featured.getDeliveryDate()));

                restaurantList.add(new DataObject()
                        .setOrder_id(featured.getId())
                        .setRider_id(featured.getRider_order_id())
                        .setCustomer_name(featured.getUser_name())
                        .setCustomer_picture(featured.getUser_picture())
                        .setObject_name(featured.getRestaurantName())
                        .setObject_logo(featured.getRestaurantLogo())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setOrder_delivery_time(featured.getDeliveryTime())
                        .setOrder_price(featured.getOrderPrice())
                        .setUser_lattitude(featured.getUser_lattitude())
                        .setUser_longitude(featured.getUser_longitude())
                        .setUser_id(featured.getUser_id())
                        .setPaymentType(featured.getPaymentType())
                        .setDate(dateTimeObject.getDate())
                        .setTime(dateTimeObject.getTime())
                        .setStreetAddressName(featured.getBilling_address())
                        .setBuildingName(featured.getBuilding_no())
                        .setAreaName(featured.getArea_name())
                        .setUnitName(featured.getFloor_name())
                        .setRiderNote(featured.getRider_note())
                        .setRestaurant_address(featured.getRestaurant_address())
                        .setRestaurant_name(featured.getRestaurantName())
                        .setRestaurant_logo(featured.getRestaurantLogo())
                        .setPhoneno(featured.getPhoneno())
                        .setDataType(requestObject.getConnection() == Constant.CONNECTION.RIDER_CURRENT_ORDER
                                ? Constant.DATA_TYPE.CURRENT_ORDER : Constant.DATA_TYPE.HISTORY_ORDER));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(riderOrderHistoryJson.getCode())
                    .setMessage(riderOrderHistoryJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RIDER_DOCUMENT
                || requestObject.getConnection() == Constant.CONNECTION.RIDER_ADD_DOCUMENT) {


            //region Rider Documents
            ArrayList<DataObject> restaurantList = new ArrayList<>();
            RiderDetailJson riderDetailJson = (RiderDetailJson) data;


            /*List of Rider Documents*/

            for (int i = 0; i < riderDetailJson.getRiderDetail().size(); i++) {

                RiderDetail riderDetail = riderDetailJson.getRiderDetail().get(i);
                restaurantList.add(new DataObject()
                        .setRider_document_id(riderDetail.getId())
                        .setRider_document(riderDetail.getRiderDocuments())
                        .setDocument_status(riderDetail.getDocumentStatus()));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(riderDetailJson.getCode())
                    .setMessage(riderDetailJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RIDER_BANK_DETAIL) {


            //region Rider Documents
            ArrayList<DataObject> restaurantList = new ArrayList<>();
            RiderBankDetailJson riderDetailJson = (RiderBankDetailJson) data;


            /*List of Rider Bank Detail*/

            for (int i = 0; i < riderDetailJson.getRiderDetail().size(); i++) {

                com.rs.doorbell.JsonUtil.RiderBankDetailUtil.RiderDetail riderDetail = riderDetailJson.getRiderDetail().get(i);
                restaurantList.add(new DataObject()
                        .setAccount_holder_id(riderDetail.getAccountHolderNameId())
                        .setAccount_holder_name(riderDetail.getHolderName())
                        .setAccount_no_id(riderDetail.getAccountNumberId())
                        .setAccount_no(riderDetail.getAccountNo())
                        .setBank_transist_id(riderDetail.getBankTransitNumberId())
                        .setBank_transist_no(riderDetail.getBankTransitNo())
                        .setBank_no_id(riderDetail.getBankNumberId())
                        .setBank_no(riderDetail.getBankNo()));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(riderDetailJson.getCode())
                    .setMessage(riderDetailJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.sendRiderAccpetNotification) {


            //region Rider Documents

            RiderBankDetailJson riderDetailJson = (RiderBankDetailJson) data;

            //endregion

            dataObject = new DataObject()
                    .setCode(riderDetailJson.getCode())
                    .setMessage(riderDetailJson.getMessage());

        }


        return dataObject;

    }


    @Override
    public String toString() {
        return "DataObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_fName='" + user_fName + '\'' +
                ", user_lName='" + user_lName + '\'' +
                ", user_password='" + user_password + '\'' +
                ", login_type='" + login_type + '\'' +
                ", user_picture='" + user_picture + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_sign_in='" + user_sign_in + '\'' +
                ", user_remember='" + user_remember + '\'' +
                ", userFavourite=" + userFavourite +
                ", user_payment_type='" + user_payment_type + '\'' +
                ", object_id='" + object_id + '\'' +
                ", object_name='" + object_name + '\'' +
                ", object_Description='" + object_Description + '\'' +
                ", object_category_name='" + object_category_name + '\'' +
                ", object_min_delivery_time='" + object_min_delivery_time + '\'' +
                ", object_min_order_price='" + object_min_order_price + '\'' +
                ", object_delivery_charges='" + object_delivery_charges + '\'' +
                ", object_expense='" + object_expense + '\'' +
                ", object_rating='" + object_rating + '\'' +
                ", object_no_of_rating='" + object_no_of_rating + '\'' +
                ", object_currency='" + object_currency + '\'' +
                ", object_currency_symbol='" + object_currency_symbol + '\'' +
                ", object_status='" + object_status + '\'' +
                ", object_latitude='" + object_latitude + '\'' +
                ", object_longitude='" + object_longitude + '\'' +
                ", object_address='" + object_address + '\'' +
                ", object_picture='" + object_picture + '\'' +
                ", object_logo='" + object_logo + '\'' +
                ", object_distance='" + object_distance + '\'' +
                ", object_tags='" + object_tags + '\'' +
                ", object_date_created='" + object_date_created + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", is_layout_01=" + is_layout_01 +
                ", dataType=" + dataType +
                ", objectArrayList=" + objectArrayList +
                ", homeList=" + homeList +
                ", favourite_id='" + favourite_id + '\'' +
                ", isFavourite=" + isFavourite +
                ", cart_id='" + cart_id + '\'' +
                ", chatting='" + chatting + '\'' +
                ", picture='" + picture + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isFrom=" + isFrom +
                ", isLongTap=" + isLongTap +
                ", isFirstItem=" + isFirstItem +
                ", isAlreadyAddedIntoCart=" + isAlreadyAddedIntoCart +
                ", isPaymentCardSelected=" + isPaymentCardSelected +
                ", noOfItemInCart='" + noOfItemInCart + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", phoneno='" + phoneno + '\'' +
                '}';
    }

    public DataObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.user_id);
        dest.writeString(this.user_fName);
        dest.writeString(this.user_lName);
        dest.writeString(this.user_password);
        dest.writeString(this.login_type);
        dest.writeString(this.user_picture);
        dest.writeString(this.user_phone);
        dest.writeString(this.user_email);
        dest.writeString(this.user_sign_in);
        dest.writeString(this.user_remember);
        dest.writeTypedList(this.userFavourite);
        dest.writeString(this.user_payment_type);
        dest.writeString(this.object_id);
        dest.writeString(this.object_name);
        dest.writeString(this.object_Description);
        dest.writeString(this.object_category_name);
        dest.writeString(this.object_min_delivery_time);
        dest.writeString(this.object_min_order_price);
        dest.writeString(this.object_delivery_charges);
        dest.writeString(this.object_expense);
        dest.writeString(this.object_rating);
        dest.writeString(this.object_no_of_rating);
        dest.writeString(this.object_currency);
        dest.writeString(this.object_currency_symbol);
        dest.writeString(this.object_status);
        dest.writeString(this.object_latitude);
        dest.writeString(this.object_longitude);
        dest.writeString(this.object_address);
        dest.writeString(this.object_picture);
        dest.writeString(this.object_logo);
        dest.writeString(this.object_distance);
        dest.writeString(this.object_tags);
        dest.writeString(this.object_date_created);
        dest.writeString(this.paymentType);
        dest.writeByte(this.is_layout_01 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeTypedList(this.objectArrayList);
        dest.writeList(this.homeList);
        dest.writeString(this.favourite_id);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
        dest.writeString(this.cart_id);
        dest.writeString(this.chatting);
        dest.writeString(this.picture);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeByte(this.isFrom ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLongTap ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFirstItem ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAlreadyAddedIntoCart ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPaymentCardSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.noOfItemInCart);
        dest.writeString(this.basePrice);
        dest.writeString(this.rider_id);
        dest.writeString(this.customer_name);
        dest.writeString(this.customer_picture);
        dest.writeString(this.rider_document_id);
        dest.writeString(this.rider_document);
        dest.writeString(this.document_status);
        dest.writeString(this.account_holder_id);
        dest.writeString(this.account_holder_name);
        dest.writeString(this.account_no_id);
        dest.writeString(this.account_no);
        dest.writeString(this.bank_no_id);
        dest.writeString(this.bank_no);
        dest.writeString(this.bank_transist_id);
        dest.writeString(this.bank_transist_no);
        dest.writeString(this.order_id);
        dest.writeString(this.order_restaurant_name);
        dest.writeString(this.restaurant_address);
        dest.writeString(this.restaurant_logo);
        dest.writeString(this.restaurant_name);
        dest.writeString(this.order_delivery_date);
        dest.writeString(this.order_delivery_time);
        dest.writeString(this.order_price);
        dest.writeString(this.delivery_time);
        dest.writeString(this.payment_type);
        dest.writeString(this.order_status);
        dest.writeDouble(this.rider_latitude);
        dest.writeDouble(this.rider_longitude);
        dest.writeString(this.buildingName);
        dest.writeString(this.streetAddressName);
        dest.writeString(this.areaName);
        dest.writeString(this.unitName);
        dest.writeString(this.riderNote);
        dest.writeString(this.user_lattitude);
        dest.writeString(this.user_longitude);
        dest.writeString(this.phoneno);


    }

    protected DataObject(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.user_id = in.readString();
        this.user_fName = in.readString();
        this.user_lName = in.readString();
        this.user_password = in.readString();
        this.login_type = in.readString();
        this.user_picture = in.readString();
        this.user_phone = in.readString();
        this.user_email = in.readString();
        this.user_sign_in = in.readString();
        this.user_remember = in.readString();
        this.userFavourite = in.createTypedArrayList(FavouriteList.CREATOR);
        this.user_payment_type = in.readString();
        this.object_id = in.readString();
        this.object_name = in.readString();
        this.object_Description = in.readString();
        this.object_category_name = in.readString();
        this.object_min_delivery_time = in.readString();
        this.object_min_order_price = in.readString();
        this.object_delivery_charges = in.readString();
        this.object_expense = in.readString();
        this.object_rating = in.readString();
        this.object_no_of_rating = in.readString();
        this.object_currency = in.readString();
        this.object_currency_symbol = in.readString();
        this.object_status = in.readString();
        this.object_latitude = in.readString();
        this.object_longitude = in.readString();
        this.object_address = in.readString();
        this.object_picture = in.readString();
        this.object_logo = in.readString();
        this.object_distance = in.readString();
        this.object_tags = in.readString();
        this.object_date_created = in.readString();
        this.paymentType = in.readString();
        this.is_layout_01 = in.readByte() != 0;
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constant.DATA_TYPE.values()[tmpDataType];
        this.objectArrayList = in.createTypedArrayList(DataObject.CREATOR);
        this.homeList = new ArrayList<Object>();
        in.readList(this.homeList, Object.class.getClassLoader());
        this.favourite_id = in.readString();
        this.isFavourite = in.readByte() != 0;
        this.cart_id = in.readString();
        this.chatting = in.readString();
        this.picture = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.isFrom = in.readByte() != 0;
        this.isLongTap = in.readByte() != 0;
        this.isFirstItem = in.readByte() != 0;
        this.isAlreadyAddedIntoCart = in.readByte() != 0;
        this.isPaymentCardSelected = in.readByte() != 0;
        this.noOfItemInCart = in.readString();
        this.basePrice = in.readString();
        this.rider_id = in.readString();
        this.customer_name = in.readString();
        this.customer_picture = in.readString();
        this.rider_document_id = in.readString();
        this.rider_document = in.readString();
        this.document_status = in.readString();
        this.account_holder_id = in.readString();
        this.account_holder_name = in.readString();
        this.account_no_id = in.readString();
        this.account_no = in.readString();
        this.bank_no_id = in.readString();
        this.bank_no = in.readString();
        this.bank_transist_id = in.readString();
        this.bank_transist_no = in.readString();
        this.order_id = in.readString();
        this.order_restaurant_name = in.readString();
        this.restaurant_address = in.readString();
        this.restaurant_logo = in.readString();
        this.restaurant_name = in.readString();
        this.order_delivery_date = in.readString();
        this.order_delivery_time = in.readString();
        this.order_price = in.readString();
        this.delivery_time = in.readString();
        this.payment_type = in.readString();
        this.order_status = in.readString();
        this.rider_latitude = in.readDouble();
        this.rider_longitude = in.readDouble();
        this.buildingName = in.readString();
        this.streetAddressName = in.readString();
        this.areaName = in.readString();
        this.unitName = in.readString();
        this.riderNote = in.readString();
        this.user_lattitude = in.readString();
        this.user_longitude = in.readString();
        this.phoneno = in.readString();
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}
