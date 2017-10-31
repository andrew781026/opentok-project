package andrew.com.riko.www.webviewproject.js;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import andrew.com.riko.www.webviewproject.VideoChatActivity;
import andrew.com.riko.www.webviewproject.properties.KeyName;

public class WebAppInterface {
    Context mContext;

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

    /** Show a toast from the web page */
    @JavascriptInterface
    public void startChat(String apiKey,String sessionId,String token) {
        // js input apiKey , sessionId , token
        Intent intent = new Intent(mContext, VideoChatActivity.class);
        intent.putExtra(KeyName.API_KEY,apiKey);
        intent.putExtra(KeyName.SESSION_ID,sessionId);
        intent.putExtra(KeyName.TOKEN,token);
        mContext.startActivity(intent);
    }


}