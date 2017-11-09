package andrew.com.riko.www.webviewproject.http;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Test on 2017/11/8.
 */

public class HttpUtils {

    public static <T> T getModel(String url,int id,Class<T> clazz){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url+"/"+id).get().build();

        try {
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ){
                String body = response.body().string();
                T t = new Gson().fromJson(body, clazz);
                return t ;
            }

        } catch (IOException e) {}

        return null ;
    }

    public static <T> List<T> getModels(String url,Class<T> clazz){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ){

                // 將 json array
                String body = response.body().string();
                Object[] array = (Object[])java.lang.reflect.Array.newInstance(clazz, 1);
                array = new Gson().fromJson(body, array.getClass());
                List list = Arrays.asList(array);
                return list;
                /*
                                List<T> list = new ArrayList<T>();
                                for (int i=0 ; i<array.length ; i++){
                                    list.add((T)array[i]);
                                }
                                return list;
                                */
            }

        } catch (IOException e) {}

        return null ;
    }

}
