package com.grabhouse.grabhouse.utilities;

public class Data {

    public static final String APP_PREF = "app_pref";

    // URLs
    public static String URL_HOST = "http://api.zeefive.com/grabhouse/v1";
    public static final String URL_REQUEST_SMS = URL_HOST + "/sms";
    public static final String URL_VERIFY_OTP = URL_HOST + "/verifyOtp";
    public static final String URL_GET_HOUSE = URL_HOST + "/house"; // GET
    public static final String URL_POST_HOUSE = URL_HOST + "/house?key=%s"; // POST

    public static final String SMS_ORIGIN = "KODWIZ";
    public static final String OTP_DELIMITER = ":";

    // RESPONSE KEYS
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SALE_TYPE = "sale_type";
    public static final String HOUSE_TYPE = "house_type";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String PRICE = "price";
    public static final String IS_OPEN = "is_open";
    public static final String ONE_BHK = "1 BHK";
    public static final String TWO_BHK = "2 BHK";
    public static final String SELL = "sell";
    public static final String RENT = "rent";

    public static final String BUY_OR_RENT = "buy_or_rent";

    // API JSON RESPONSE TAGS
    public static final String CODE = "code";
    public static final String MESSAGE = "message";


}
