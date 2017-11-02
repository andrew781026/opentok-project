package andrew.com.riko.www.webviewproject.service;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

//  此 service 用取得裝置的 InstanceId
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("getToken", "refresh token:"+refreshedToken);
    }

}
