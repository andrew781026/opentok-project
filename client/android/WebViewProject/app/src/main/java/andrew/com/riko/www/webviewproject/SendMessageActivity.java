package andrew.com.riko.www.webviewproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

    }

    public void sendMessage(View v){

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this,"refreshedToken="+refreshedToken,Toast.LENGTH_SHORT).show();
        Log.i("refreshedToken=",refreshedToken);

        this.sendMessage(refreshedToken);

        // 必須將 token 存到某台server 中
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private void sendMessage(final String token){

        final String url = "https://fcm.googleapis.com/fcm/send";

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                String severKey = "AAAAn7TpWdg:APA91bFDBFORFGn2zkaX4n8Sz4xmgek6NyOAYS5JDeR1c6pxODISfzJyHWEPNLwR7XFoB-vMDQVrE4a0F9XYpqOc8lmfn8p6hqzBsCd2zaKLu68GclYYspIJN0SdqQGj-JlPokvRP-X_";

                String json = "{\n" +
                        "  \"to\": \""+token+"\",\n" +
                        "  \"notification\": {\n" +
                        "    \"body\": \"great match!\",\n" +
                        "    \"title\": \"Portugal vs. Denmark\",\n" +
                        "    \"icon\": \"myicon\"\n" +
                        "  },\n" +
                        "  \"data\": {\n" +
                        "    \"Nick\": \"Mario\",\n" +
                        "    \"Room\": \"PortugalVSDenmark\"\n" +
                        "  }\n" +
                        "}\n";

                RequestBody formBody = RequestBody.create(JSON,json);

                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type","application/json")
                        .addHeader("Authorization","key="+severKey)
                        .post(formBody)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();


    }

}
