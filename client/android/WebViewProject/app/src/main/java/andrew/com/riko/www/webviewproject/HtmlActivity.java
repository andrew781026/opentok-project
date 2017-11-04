package andrew.com.riko.www.webviewproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;

import andrew.com.riko.www.webviewproject.js.WebAppInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HtmlActivity extends AppCompatActivity {

    WebView myWebView ;
    String url = "http://emarket.riko.com.tw:8444/Search_BILL_OF_PURCHASE/yiyo/client-call-view.html";
    // String url = "https://www.yoecare.com/client";

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message inputMessage) {

            Thread thread = new Thread(new Runnable() {

                private boolean checkUrlConnectable(String url) throws IOException {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    if ( response.code() == 200 ) return true ;
                    else return false ;
                }

                private int getResponseCode(String url) {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        return response.code() ;
                    } catch (IOException e) {
                        return 404 ;
                    }
                }

                @Override
                public void run() {
                    int responseCode = this.getResponseCode(url) ;


                    if ( responseCode == 200 ){

                        // 新的執行續不能更新 ui
                        HtmlActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                HtmlActivity.this.settingWebView(url);
                            }
                        });

                    }else {

                        // 新的執行續不能更新 ui
                        HtmlActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 連線失敗 , 就顯示 dialog 跟關閉 APP
                                HtmlActivity.this.showDialog();
                            }
                        });
                    }
                }
            });
            thread.start();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 先呼叫 this.requestWindowFeature(Window.FEATURE_NO_TITLE) 再呼叫 this.setContentView(R.layout.layout_name).
        //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_html);
        this.getSupportActionBar().hide();

        myWebView = (WebView) super.findViewById(R.id.webview);
        myWebView.loadUrl("http://www.google.com");
        mHandler.handleMessage(new Message());

    }

    private void showDialog(){

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        builder.setNeutralButton("離開程式",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                HtmlActivity.this.finish();
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void settingWebView(String url){

        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(HtmlActivity.this), "Android");

    }

    private class MyWebViewClient extends WebViewClient {

        //  url 的解決方案

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Toast.makeText(HtmlActivity.this,Uri.parse(url).getHost(),Toast.LENGTH_SHORT).show();

            if (Uri.parse(url).getHost().contains("sss.gotochatroom.yy")) {

                Intent intent = new Intent("android.intent.action.HAHA");
                startActivity(intent);
                // true = WebView do not load the page
                return true;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // startActivity(intent);
            return super.shouldOverrideUrlLoading(view,url);
        }

    }


}
