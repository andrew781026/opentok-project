package andrew.com.riko.www.webviewproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdviceActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 19;
    private static final int RESULT_GET_IMG = 55;
    private static final int REQUEST_CAMERA = 73;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                    */
            }
            return false;
        }

    };

    public void takePicture(View v){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 版本大於 android 6.0 且 尚無授權者 , 才需要 requestPermissions
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
        }else {
            this.openCameraApp();
        }
    }

    private void openCameraApp(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, REQUEST_CAMERA);//zero can be replaced with any action code
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Now user should be able to use camera
            this.openCameraApp();
        }
        else {
            // Your app will not have this permission. Turn off all functions
            // that require this permission or it will force close like your
            // original question
            Toast.makeText(this,"相機的使用沒有授權,無法拍照",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.adviceNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
