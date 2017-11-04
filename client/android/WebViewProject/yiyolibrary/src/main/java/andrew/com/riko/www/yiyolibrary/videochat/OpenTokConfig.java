package andrew.com.riko.www.yiyolibrary.videochat;

import android.webkit.URLUtil;

public class OpenTokConfig {
    // *** Fill the following variables using your own Project info from the OpenTok dashboard  ***
    // ***                      https://dashboard.tokbox.com/projects                           ***

    // Replace with your OpenTok API key
    public static final String API_KEY = "45988592";
    // Replace with a generated Session ID
    public static final String SESSION_ID = "1_MX40NTk4ODU5Mn5-MTUwOTAwODUxOTgyNn53bXJSdXFVcndpMDlZbTdXd0NFU1BHTkJ-fg";
    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    public static final String TOKEN = "T1==cGFydG5lcl9pZD00NTk4ODU5MiZzaWc9ODllOWJmYzMxYTllNDY3ZGU2ZDkxMjM2NWQ5N2M3ZDdlOGU4YTAyMTpzZXNzaW9uX2lkPTFfTVg0ME5UazRPRFU1TW41LU1UVXdPVEF3T0RVeE9UZ3lObjUzYlhKU2RYRlZjbmRwTURsWmJUZFhkME5GVTFCSFRrSi1mZyZjcmVhdGVfdGltZT0xNTA5MDA4NTQ4Jm5vbmNlPTAuNzczODg0NTc0NDA4NDImcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTUxMTYwMDU0OCZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";

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
