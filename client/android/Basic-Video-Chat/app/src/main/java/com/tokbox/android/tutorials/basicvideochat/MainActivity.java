package com.tokbox.android.tutorials.basicvideochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    // Suppressing this warning. mWebServiceCoordinator will get GarbageCollected if it is local.
    @SuppressWarnings("FieldCanBeLocal")
    private WebServiceCoordinator mWebServiceCoordinator;

    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private Subscriber mSubscriber2;

    String chatServerUrl;
    private static Map<String,Stream> streamHashMap = new HashMap<>();

    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private FrameLayout mSubscriber2ViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize view objects from your layout
        mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);
        mSubscriber2ViewContainer = (FrameLayout)findViewById(R.id.subscriber_container2);

        Intent intent = super.getIntent();

        chatServerUrl = intent.getStringExtra("CHAT_SERVER_URL");
        chatServerUrl = "http://"+chatServerUrl;
        Toast.makeText(this,"chatServerUrl 是 "+chatServerUrl,Toast.LENGTH_SHORT).show();

        requestPermissions();
    }

     /* Activity lifecycle methods */

    @Override
    protected void onPause() {

        Log.d(LOG_TAG, "onPause");

        super.onPause();

        if (mSession != null) {
            mSession.onPause();
        }

    }

    @Override
    protected void onResume() {

        Log.d(LOG_TAG, "onResume");

        super.onResume();

        if (mSession != null) {
            mSession.onResume();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        Log.d(LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Log.d(LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel))
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {

        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // if there is no server URL set
            if (chatServerUrl == null) {
                // use hard coded session values
                if (OpenTokConfig.areHardCodedConfigsValid()) {
                    initializeSession(OpenTokConfig.API_KEY, OpenTokConfig.SESSION_ID, OpenTokConfig.TOKEN);
                } else {
                    showConfigError("Configuration Error", OpenTokConfig.hardCodedConfigErrorMessage);
                }
            } else {
                // otherwise initialize WebServiceCoordinator and kick off request for session data
                // session initialization occurs once data is returned, in onSessionConnectionDataReady
                if (OpenTokConfig.isWebServerConfigUrlValid(chatServerUrl)) {
                    mWebServiceCoordinator = new WebServiceCoordinator(this, webserviceListener);
                    // mWebServiceCoordinator.fetchSessionConnectionData(OpenTokConfig.SESSION_INFO_ENDPOINT);
                    mWebServiceCoordinator.fetchSessionConnectionData(chatServerUrl+ "/session");
                } else {
                    showConfigError("Configuration Error", OpenTokConfig.webServerConfigErrorMessage);
                }
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), RC_VIDEO_APP_PERM, perms);
        }
    }

    private void initializeSession(String apiKey, String sessionId, String token) {

        mSession = new Session.Builder(this, apiKey, sessionId).build();
        mSession.setSessionListener(sessionListener);
        mSession.connect(token);
    }

    private WebServiceCoordinator.Listener webserviceListener = new WebServiceCoordinator.Listener(){

          /* Web Service Coordinator delegate methods */

        @Override
        public void onSessionConnectionDataReady(String apiKey, String sessionId, String token) {

            Log.d(LOG_TAG, "ApiKey: "+apiKey + " SessionId: "+ sessionId + " Token: "+token);
            initializeSession(apiKey, sessionId, token);
        }

        @Override
        public void onWebServiceCoordinatorError(Exception error) {

            Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            Toast.makeText(MainActivity.this, "Web Service error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            finish();

        }

    };

    private Session.SessionListener sessionListener = new Session.SessionListener(){

        /* Session Listener methods */
        // --------------------------  SessionListener methods  ---------------------------------------

        @Override
        public void onConnected(Session session) {

            Log.d(LOG_TAG, "onConnected: Connected to session: "+session.getSessionId());

            // initialize Publisher and set this object to listen to Publisher events
            mPublisher = new Publisher.Builder(MainActivity.this).build();
            mPublisher.setPublisherListener(publisherListener);

            // set publisher video style to fill view
            mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                    BaseVideoRenderer.STYLE_VIDEO_FILL);
            mPublisherViewContainer.addView(mPublisher.getView());

            mSession.publish(mPublisher);
        }

        @Override
        public void onDisconnected(Session session) {

            Log.d(LOG_TAG, "onDisconnected: Disconnected from session: "+session.getSessionId());
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {

            Log.d(LOG_TAG, "onStreamReceived: New Stream Received "+stream.getStreamId() + " in session: "+session.getSessionId());

            Toast.makeText( MainActivity.this , "開始有 StreamId()="+stream.getStreamId(),Toast.LENGTH_SHORT ).show();

            //  驗證進來的 stream 是否已經跟 mSubscriber 或 mSubscriber2 連動
            Stream stream2 = streamHashMap.get( stream.getStreamId() );

            if ( stream2 == null ){

                if (mSubscriber == null ) {
                    mSubscriber = new Subscriber.Builder(MainActivity.this, stream).build();
                    mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                    mSession.subscribe(mSubscriber);
                    mSubscriberViewContainer.addView(mSubscriber.getView());
                }else if (mSubscriber2 == null ) {
                    mSubscriber2 = new Subscriber.Builder(MainActivity.this, stream).build();
                    mSubscriber2.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                    mSession.subscribe(mSubscriber2);
                    mSubscriber2ViewContainer.addView(mSubscriber2.getView());
                }
                streamHashMap.put(stream.getStreamId(),stream);
            }


        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {

            Log.d(LOG_TAG, "onStreamDropped: Stream Dropped: "+stream.getStreamId() +" in session: "+session.getSessionId());

            Toast.makeText( MainActivity.this , "StreamId()="+stream.getStreamId()+"掉線",Toast.LENGTH_SHORT ).show();

            if (mSubscriber != null && mSubscriber.getStream() !=null && mSubscriber.getStream().getStreamId().equals(stream.getStreamId())) {
                mSubscriber = null;
                mSubscriberViewContainer.removeAllViews();
            }else if ( mSubscriber2 != null && mSubscriber2.getStream() !=null && mSubscriber2.getStream().getStreamId().equals(stream.getStreamId())){
                mSubscriber2 = null;
                mSubscriber2ViewContainer.removeAllViews();
            }
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            Log.e(LOG_TAG, "onError: "+ opentokError.getErrorDomain() + " : " +
                    opentokError.getErrorCode() + " - "+opentokError.getMessage() + " in session: "+ session.getSessionId());

            showOpenTokError(opentokError);
        }

        // --------------------------  SessionListener methods  ---------------------------------------


    };

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener(){

          /* Publisher Listener methods */
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

            Log.d(LOG_TAG, "onStreamCreated: Publisher Stream Created. Own stream "+stream.getStreamId());

        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

            Log.d(LOG_TAG, "onStreamDestroyed: Publisher Stream Destroyed. Own stream "+stream.getStreamId());
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {

            Log.e(LOG_TAG, "onError: "+opentokError.getErrorDomain() + " : " +
                    opentokError.getErrorCode() +  " - "+opentokError.getMessage());

            showOpenTokError(opentokError);
        }
    };

    private void showOpenTokError(OpentokError opentokError) {

        Toast.makeText(this, opentokError.getErrorDomain().name() +": " +opentokError.getMessage() + " Please, see the logcat.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void showConfigError(String alertTitle, final String errorMessage) {
        Log.e(LOG_TAG, "Error " + alertTitle + ": " + errorMessage);
        new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage(errorMessage)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
