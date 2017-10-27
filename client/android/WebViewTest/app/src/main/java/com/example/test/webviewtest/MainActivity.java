package com.example.test.webviewtest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = (WebView) super.findViewById(R.id.webview);
        myWebView.loadUrl("http://emarket.riko.com.tw:8444/Search_BILL_OF_PURCHASE/temp.html");
        myWebView.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }


    private class MyWebViewClient extends WebViewClient {

        // js or url 的解決方案

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Toast.makeText(MainActivity.this,Uri.parse(url).getHost(),Toast.LENGTH_SHORT).show();

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
