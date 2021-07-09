package com.aimers.zone.Utils;

public class Constant {
    //Urls
    public static final String BASE_URL = "https://22942209db28.ngrok.io";
    public static final String REGISTER_URL = BASE_URL+"/ptz/api/register.php";
    public static final String GET_USER_INFO = BASE_URL+"/ptz/api/get-user.php";
    public static final String LOGIN_URL = BASE_URL+"/ptz/api/login.php";
    public static final String GAME_URL = BASE_URL+"/ptz/api/game.php";
    public static final String MATCH_URL = BASE_URL+"/ptz/api/matches.php";
    public static final String MATCH_JOIN_URL = BASE_URL+"/ptz/api/join-match.php";
    public static final String PAYMENT_INIT = BASE_URL+"/ptz/api/paytm/initiate-payment.php";
    public static final String WALLET_URL = BASE_URL+"/ptz/api/initiate-wallet.php";

    //application type
    public static final String DEVICE_TYPE = "ANDROID";


    public static final String[] MATCH_STATUS = new String[]{"ONGOING", "UPCOMING","COMPLETED"};
}
