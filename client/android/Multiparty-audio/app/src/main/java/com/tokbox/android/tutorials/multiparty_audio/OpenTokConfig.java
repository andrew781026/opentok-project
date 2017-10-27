package com.tokbox.android.tutorials.multiparty_audio;

import android.webkit.URLUtil;

// apiKey 已過期
public class OpenTokConfig {
    // *** Fill the following variables using your own Project info from the OpenTok dashboard  ***
    // ***                      https://dashboard.tokbox.com/projects                           ***

    // Replace with your OpenTok API key
    public static final String API_KEY = "45932562";
    // Replace with a generated Session ID
    public static final String SESSION_ID = "1_MX40NTkzMjU2Mn5-MTUwMzQ5MTM0ODY0OH5xZ2lvUkpjNzFYbTYvOWE1OFFXUXh5bmh-fg";
    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    public static final String TOKEN = "T1==cGFydG5lcl9pZD00NTkzMjU2MiZzaWc9Y2QzMDU1YzA3OTUwZjcwODlmYzNhNjBmNzk1MTYxNGJlNjc2M2Q3MTpzZXNzaW9uX2lkPTFfTVg0ME5Ua3pNalUyTW41LU1UVXdNelE1TVRNME9EWTBPSDV4WjJsdlVrcGpOekZZYlRZdk9XRTFPRkZYVVhoNWJtaC1mZyZjcmVhdGVfdGltZT0xNTAzNDkxNDAxJm5vbmNlPTAuMDY4MTQxOTc3OTE2MzU0MzUmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTUwNDA5NjIwMSZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";

    /*                           ***** OPTIONAL *****
     If you have set up a server to provide session information replace the null value
     in CHAT_SERVER_URL with it.

     For example: "https://yoursubdomain.com"
    */
    public static final String CHAT_SERVER_URL = "http://192.168.43.33:3000";
    public static final String SESSION_INFO_ENDPOINT = CHAT_SERVER_URL + "/session";


    // *** The code below is to validate this configuration file. You do not need to modify it  ***

    public static String webServerConfigErrorMessage;
    public static String hardCodedConfigErrorMessage;

    public static boolean areHardCodedConfigsValid() {
        if (OpenTokConfig.API_KEY != null && !OpenTokConfig.API_KEY.isEmpty()
                && OpenTokConfig.SESSION_ID != null && !OpenTokConfig.SESSION_ID.isEmpty()
                && OpenTokConfig.TOKEN != null && !OpenTokConfig.TOKEN.isEmpty()) {
            return true;
        }
        else {
            hardCodedConfigErrorMessage = "API KEY, SESSION ID and TOKEN in OpenTokConfig.java cannot be null or empty.";
            return false;
        }
    }

    public static boolean isWebServerConfigUrlValid(){
        if (OpenTokConfig.CHAT_SERVER_URL == null || OpenTokConfig.CHAT_SERVER_URL.isEmpty()) {
            webServerConfigErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must not be null or empty";
            return false;
        } else if ( !( URLUtil.isHttpsUrl(OpenTokConfig.CHAT_SERVER_URL) || URLUtil.isHttpUrl(OpenTokConfig.CHAT_SERVER_URL)) ) {
            webServerConfigErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must be specified as either http or https";
            return false;
        } else if ( !URLUtil.isValidUrl(OpenTokConfig.CHAT_SERVER_URL) ) {
            webServerConfigErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java is not a valid URL";
            return false;
        } else {
            return true;
        }
    }

    public static boolean isWebServerConfigUrlValid(String chatServerUrl){
        if (chatServerUrl == null || chatServerUrl.isEmpty()) {
            webServerConfigErrorMessage = "chatServerUrl in OpenTokConfig.java must not be null or empty";
            return false;
        } else if ( !URLUtil.isHttpsUrl(chatServerUrl) && !URLUtil.isHttpUrl(chatServerUrl) ) {
            webServerConfigErrorMessage = "chatServerUrl in OpenTokConfig.java must be specified as either http or https";
            return false;
        } else if ( !URLUtil.isValidUrl(chatServerUrl) ) {
            webServerConfigErrorMessage = "chatServerUrl in OpenTokConfig.java is not a valid URL";
            return false;
        } else {
            return true;
        }
    }

}
