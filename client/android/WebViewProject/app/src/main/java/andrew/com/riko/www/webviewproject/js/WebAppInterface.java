package andrew.com.riko.www.webviewproject.js;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import andrew.com.riko.www.webviewproject.MultiVideoChatActivity;
import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import andrew.com.riko.www.webviewproject.properties.KeyName;


public class WebAppInterface {
    Context mContext;
    // private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("android.intent.action.HAHA");
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void gotoPhoneCallPage(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 進入此區塊 , 代表你沒有得到授權打電話
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else {
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void startChat(String apiKey,String sessionId,String token,String roomName) {
        // js input apiKey , sessionId , token
        Toast.makeText(mContext,"in startChat",Toast.LENGTH_SHORT).show();
        VideoConnectInfo videoConnectInfo = new VideoConnectInfo(apiKey,sessionId,token,roomName);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyName.VIDEO_CONNECT_INFO,videoConnectInfo);
        bundle.putString(KeyName.API_KEY,apiKey);
        bundle.putString(KeyName.SESSION_ID,sessionId);
        bundle.putString(KeyName.TOKEN,token);
        bundle.putString(KeyName.ROOM_NAME,/* sdf.format(new Date()) + */ roomName);
        Intent intent = new Intent(mContext, MultiVideoChatActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void gotoVideoChat(String restServerUrl,String roomName) {
        Toast.makeText(mContext, "restServerUrl="+restServerUrl, Toast.LENGTH_SHORT).show();
        // js input apiKey , sessionId , token
        Intent intent = new Intent(mContext, MultiVideoChatActivity.class);
        intent.putExtra(KeyName.SERVER_URL,restServerUrl);
        intent.putExtra(KeyName.ROOM_NAME,roomName);
        mContext.startActivity(intent);
    }


}