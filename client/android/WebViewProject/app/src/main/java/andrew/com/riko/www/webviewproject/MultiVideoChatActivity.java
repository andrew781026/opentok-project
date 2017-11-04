package andrew.com.riko.www.webviewproject;

import android.Manifest;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import andrew.com.riko.www.webviewproject.model.VideoConnectInfo;
import andrew.com.riko.www.webviewproject.properties.KeyName;
import andrew.com.riko.www.webviewproject.utils.StringUtils;
import andrew.com.riko.www.webviewproject.videochat.ConstraintSetHelper;
import andrew.com.riko.www.webviewproject.videochat.OpenTokConfig;
import andrew.com.riko.www.webviewproject.videochat.WebServiceCoordinator;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MultiVideoChatActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks,Session.SessionListener,PublisherKit.PublisherListener {

    private static final String TAG = MultiVideoChatActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 800; // 螢幕刷新速度
    private static final int RC_VIDEO_APP_PERM = 800;

    // Suppressing this warning. mWebServiceCoordinator will get GarbageCollected if it is local.
    @SuppressWarnings("FieldCanBeLocal")
    private WebServiceCoordinator mWebServiceCoordinator;

    private Session mSession;
    private Publisher mPublisher;
    // private String restfulServerUrl = "https://oriact-video-chat-project.herokuapp.com/";
    private String restfulServerUrl = null;
    // private String roomName = "bigRoom";
    private String roomName = null;
    // private boolean isTest = false;
    private VideoConnectInfo videoConnectInfo = null ;

    private ArrayList<Subscriber> mSubscribers = new ArrayList<Subscriber>();
    private HashMap<Stream, Subscriber> mSubscriberStreams = new HashMap<Stream, Subscriber>();

    private ConstraintLayout mContainer;

    //----------------------------methods for Session.SessionListener ----------------------------------------

    @Override
    public void onConnected(Session session) {
        Log.d(TAG, "onConnected: Connected to session " + session.getSessionId());

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());

        mSession = null;
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in session " + session.getSessionId());

        Toast.makeText(MultiVideoChatActivity.this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(TAG, "onStreamReceived: New stream " + stream.getStreamId() + " in session " + session.getSessionId());

        final Subscriber subscriber = new Subscriber.Builder(MultiVideoChatActivity.this, stream).build();
        mSession.subscribe(subscriber);
        mSubscribers.add(subscriber);
        mSubscriberStreams.put(stream, subscriber);

        int subId = getResIdForSubscriberIndex(mSubscribers.size() - 1);
        subscriber.getView().setId(subId);
        mContainer.addView(subscriber.getView());

        calculateLayout();
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());

        Subscriber subscriber = mSubscriberStreams.get(stream);
        if (subscriber == null) {
            return;
        }

        mSubscribers.remove(subscriber);
        mSubscriberStreams.remove(stream);
        mContainer.removeView(subscriber.getView());

        // Recalculate view Ids
        for (int i = 0; i < mSubscribers.size(); i++) {
            mSubscribers.get(i).getView().setId(getResIdForSubscriberIndex(i));
        }
        calculateLayout();
    }

    //----------------------------------methods for Session.SessionListener ------------------------------------------------------

    private WebServiceCoordinator.Listener webserviceListener = new WebServiceCoordinator.Listener(){

          /* Web Service Coordinator delegate methods */

        @Override
        public void onSessionConnectionDataReady(String apiKey, String sessionId, String token) {

            Log.d( MultiVideoChatActivity.class.getSimpleName() , "ApiKey: "+apiKey + " SessionId: "+ sessionId + " Token: "+token);
            initializeSession(apiKey, sessionId, token);
        }

        @Override
        public void onWebServiceCoordinatorError(Exception error) {

            Log.e( MultiVideoChatActivity.class.getSimpleName() , "Web Service error: " + error.getMessage());
            Toast.makeText(MultiVideoChatActivity.this, "Web Service error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            finish();

        }

    };

    //----------------------------------PublisherKit.PublisherListener------------------------------------------------------
    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in publisher");

        Toast.makeText(this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        finish();
    }
    //----------------------------------PublisherKit.PublisherListener------------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // --------------------------- menu 資源綁定 ---------------------------------
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_menu, menu);
        return true;
    }
    // --------------------------- menu 資源綁定 ---------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // --------------------------- menu item 點擊處理 ---------------------------------
        switch (item.getItemId()) {
            case R.id.goHome:
                Intent intent = new Intent(this,GetTokenActivity.class);
                startActivity(intent);
                return true;
            case R.id.goWebApp:
                Intent webIntent = new Intent("android.intent.action.myWeb");
                startActivity(webIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_chat_layout);

        mContainer = (ConstraintLayout) findViewById(R.id.main_container);

        Intent intent = super.getIntent();

        String serverUrl = intent.getStringExtra(KeyName.SERVER_URL);
        String roomName = intent.getStringExtra(KeyName.SERVER_URL);
        videoConnectInfo = (VideoConnectInfo) intent.getSerializableExtra(KeyName.VIDEO_CONNECT_INFO);

        if ( !StringUtils.isBlank(serverUrl) ) restfulServerUrl = "http://"+ serverUrl;
        if ( !StringUtils.isBlank(roomName) ) this.roomName = roomName;

        requestPermissions();
    }

    @Override
    protected void onResume() {

        Log.d(TAG, "onResume");

        super.onResume();

        if (mSession == null) {
            Log.d(TAG, "mSession is null in onResume");
            return;
        }
        mSession.onResume();

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        super.onPause();

        if (mSession == null) {
            return;
        }
        mSession.onPause();

        if (isFinishing()) {
            disconnectSession();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d(TAG,"keyCode = "+keyCode);
        switch ( keyCode ){
            case  KeyEvent.KEYCODE_HOME :
                keyCode = KeyEvent.KEYCODE_BACK ;
                Log.d(TAG,"KeyEvent.KEYCODE_HOME ");
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onUserLeaveHint() {
        Log.d(TAG,"onUserLeaveHint ");
        // Intent intent = new Intent(this,MultiVideoChatActivity.class);
        // startActivity(intent);
        finish();
        super.onUserLeaveHint();

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        disconnectSession();

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

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

    private void startPublisherPreview() {
        mPublisher = new Publisher.Builder(this).name("publisher").build();

        mPublisher.setPublisherListener(this);
        mPublisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        mPublisher.startPreview();
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(this, perms)) {

            if (restfulServerUrl != null) {
                if (OpenTokConfig.isWebServerConfigUrlValid(restfulServerUrl)) {
                    mWebServiceCoordinator = new WebServiceCoordinator(this, webserviceListener);
                    String tokenUrl = restfulServerUrl + "/room/"+URLEncoder.encode(roomName); ;
                    mWebServiceCoordinator.fetchSessionConnectionData(tokenUrl);
                } else {
                    Toast.makeText(this,"cannot connect to "+ restfulServerUrl,Toast.LENGTH_SHORT).show();
                }
            }else {
                // 用 videoConnectInfo 當參數做
                if ( videoConnectInfo != null && videoConnectInfo.isConnectable() ){

                    // String apiKey = OpenTokConfig.API_KEY;
                    // String sessionId = OpenTokConfig.SESSION_ID;
                    // String token = OpenTokConfig.TOKEN;

                    String apiKey = videoConnectInfo.getApiKey();
                    String sessionId = videoConnectInfo.getSessionId();
                    String token = videoConnectInfo.getToken();

                    initializeSession(apiKey, sessionId, token);
                }

            }

            startPublisherPreview();
            mPublisher.getView().setId(R.id.publisher_view_id);
            mContainer.addView(mPublisher.getView());
            calculateLayout();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), RC_VIDEO_APP_PERM, perms);
        }
    }

    private void initializeSession(String apiKey, String sessionId, String token) {

        mSession = new Session.Builder(this, apiKey, sessionId).sessionOptions(new Session.SessionOptions() {
            @Override
            public boolean useTextureViews() {
                return true;
            }
        }).build();
        mSession.setSessionListener(this);
        mSession.connect(token);
    }

    private int getResIdForSubscriberIndex(int index) {
        TypedArray arr = getResources().obtainTypedArray(R.array.subscriber_view_ids);
        int subId = arr.getResourceId(index, 0);
        arr.recycle();
        return subId;
    }

    private void calculateLayout() {
        ConstraintSetHelper set = new ConstraintSetHelper(R.id.main_container);

        int size = mSubscribers.size();
        if (size == 0) {
            // Publisher full screen
            set.layoutViewFullScreen(R.id.publisher_view_id);
        } else if (size == 1) {
            // Publisher
            // Subscriber
            set.layoutViewAboveView(R.id.publisher_view_id, getResIdForSubscriberIndex(0));
            set.layoutViewWithTopBound(R.id.publisher_view_id, R.id.main_container);
            set.layoutViewWithBottomBound(getResIdForSubscriberIndex(0), R.id.main_container);
            set.layoutViewAllContainerWide(R.id.publisher_view_id, R.id.main_container);
            set.layoutViewAllContainerWide(getResIdForSubscriberIndex(0), R.id.main_container);

        } else if (size > 1 && size % 2 == 0){
            //  Publisher
            // Sub1 | Sub2
            // Sub3 | Sub4
            //    .....
            set.layoutViewWithTopBound(R.id.publisher_view_id, R.id.main_container);
            set.layoutViewAllContainerWide(R.id.publisher_view_id, R.id.main_container);

            for (int i = 0; i < size; i+=2) {
                if (i == 0) {
                    set.layoutViewAboveView(R.id.publisher_view_id, getResIdForSubscriberIndex(i));
                    set.layoutViewAboveView(R.id.publisher_view_id, getResIdForSubscriberIndex(i+1));
                } else {
                    set.layoutViewAboveView(getResIdForSubscriberIndex(i-2), getResIdForSubscriberIndex(i));
                    set.layoutViewAboveView(getResIdForSubscriberIndex(i-1), getResIdForSubscriberIndex(i+1));
                }

                set.layoutTwoViewsOccupyingAllRow(getResIdForSubscriberIndex(i), getResIdForSubscriberIndex(i+1));
            }

            set.layoutViewWithBottomBound(getResIdForSubscriberIndex(size - 2), R.id.main_container);
            set.layoutViewWithBottomBound(getResIdForSubscriberIndex(size - 1), R.id.main_container);
        } else if (size > 1) {
            // Pub  | Sub1
            // Sub2 | Sub3
            // Sub3 | Sub4
            //    .....

            set.layoutViewWithTopBound(R.id.publisher_view_id, R.id.main_container);
            set.layoutViewWithTopBound(getResIdForSubscriberIndex(0), R.id.main_container);
            set.layoutTwoViewsOccupyingAllRow(R.id.publisher_view_id, getResIdForSubscriberIndex(0));

            for (int i = 1; i < size; i+=2) {
                if (i == 1) {
                    set.layoutViewAboveView(R.id.publisher_view_id, getResIdForSubscriberIndex(i));
                    set.layoutViewAboveView(getResIdForSubscriberIndex(0), getResIdForSubscriberIndex(i+1));
                } else {
                    set.layoutViewAboveView(getResIdForSubscriberIndex(i-2), getResIdForSubscriberIndex(i));
                    set.layoutViewAboveView(getResIdForSubscriberIndex(i-1), getResIdForSubscriberIndex(i+1));
                }
                set.layoutTwoViewsOccupyingAllRow(getResIdForSubscriberIndex(i), getResIdForSubscriberIndex(i+1));
            }

            set.layoutViewWithBottomBound(getResIdForSubscriberIndex(size - 2), R.id.main_container);
            set.layoutViewWithBottomBound(getResIdForSubscriberIndex(size - 1), R.id.main_container);
        }

        set.applyToLayout(mContainer, true);
    }

    private void disconnectSession() {
        if (mSession == null) {
            return;
        }

        if (mSubscribers.size() > 0) {
            for (Subscriber subscriber : mSubscribers) {
                if (subscriber != null) {
                    mSession.unsubscribe(subscriber);
                    subscriber.destroy();
                }
            }
        }

        if (mPublisher != null) {
            mSession.unpublish(mPublisher);
            mContainer.removeView(mPublisher.getView());
            mPublisher.destroy();
            mPublisher = null;
        }
        mSession.disconnect();
    }
}