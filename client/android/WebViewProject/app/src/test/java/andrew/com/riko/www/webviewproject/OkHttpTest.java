package andrew.com.riko.www.webviewproject;

import android.content.Intent;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;

import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Test on 2017/11/3.
 */

public class OkHttpTest {

    private static int taskId = 2;
    private static String url = "http://www.yoecare.com/calls/" + taskId;

    @Test
    public void getVideoConnectInfo() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.code() == 200) {

                String body = response.body().string();
                Gson gson = new Gson();
                VideoConnectInfo videoConnectInfo = gson.fromJson(body, VideoConnectInfo.class);
                System.out.println("videoConnectInfo = " + videoConnectInfo);

            }

        } catch (IOException ioe) {}

    }

}
