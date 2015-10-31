package com.grabhouse.grabhouse.utilities;

public class Data {

    public static final String APP_PREF = "app_pref";

    // URLs
    public static String URL_HOST = "http://api.zeefive.com/chicken/v1";
    public static final String URL_REQUEST_SMS = URL_HOST + "/sms";
    public static final String URL_VERIFY_OTP = URL_HOST + "/verifyOtp";

    public static final String SMS_ORIGIN = "KODWIZ";
    public static final String OTP_DELIMITER = ":";

    // MENUs
    public static String[] settingsMenuList = new String[]{"Location", "Account", "Other"};

    // Dialogs
    public static final String TITLE_LOCATION_OFF = "Location is Off";
    public static final String MESSAGE_LOCATION_OFF = "Turn On Location Services to Allow this app to Determine Your Location";

    //DATABASE FIELD NAMES
    // ITEM_TABLE
    public static final String TABLE_ITEMS_ID = "item_id";
    public static final String TABLE_ITEMS_NAME = "name";
    public static final String TABLE_ITEMS_DESCRIPTION = "description";
    public static final String TABLE_ITEMS_IMAGE = "image";

    // REVIEWS_TABLE
    public static final String TABLE_REVIEWS_ID = "review_id";
    public static final String TABLE_REVIEWS_REVIEW = "review";
    public static final String TABLE_REVIEWS_STAR = "star";

    // SHOPITEMS_TABLE
    public static final String TABLE_SHOPITEMS_ID = "shopitem_id";
    public static final String TABLE_SHOPITEMS_RATE = "rate";

    // RESPONSE KEYS
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String LOGO = "logo";
    public static final String RATING = "rating";
    public static final String ITEMS = "items";
    public static final String DESC = "desc";
    public static final String IMAGE = "image";
    public static final String PRICE = "price";


    // USERS_TABLE
    public static final String TABLE_USERS_ID = "user_id";
    public static final String TABLE_USERS_NAME = "name";
    public static final String TABLE_USERS_EMAIL = "email";
    public static final String TABLE_USERS_PASSWORD = "password";
    public static final String TABLE_USERS_SALT = "salt";
    public static final String TABLE_USERS_PHONE = "phone";
    public static final String TABLE_USERS_IMAGE_USER = "image_user";
    public static final String TABLE_USERS_CITY = "city";
    public static final String TABLE_USERS_STREET = "street";
    public static final String TABLE_USERS_LANDMARK = "landmark";
    public static final String TABLE_USERS_PIN = "pin";

    // API JSON RESPONSE TAGS
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String SHOPS = "shops";


}
