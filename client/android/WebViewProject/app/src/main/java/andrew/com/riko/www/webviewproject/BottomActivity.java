package andrew.com.riko.www.webviewproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import andrew.com.riko.www.webviewproject.utils.BottomNavigationViewHelper;


public class BottomActivity extends AppCompatActivity {

    private TextView mTextMessage;
    // private List<Fragment> fragmentList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_call:
                    mTextMessage.setText(R.string.menu_call);
                    BottomActivity.this.switchFragment(R.id.fragment,new CallFragment());
                    return true;
                case R.id.action_appointment:
                    mTextMessage.setText(R.string.menu_appointment);
                    BottomActivity.this.switchFragment(R.id.fragment,new AppointmentFragment());
                    return true;
                case R.id.action_history:
                    mTextMessage.setText(R.string.menu_history);
                    BottomActivity.this.switchFragment(R.id.fragment,new HistoryFragment());
                    return true;
                case R.id.action_message:
                    mTextMessage.setText(R.string.menu_message);
                    BottomActivity.this.switchFragment(R.id.fragment,new MessageFragment());
                    return true;
                case R.id.action_ask:
                    Intent intent = new Intent(BottomActivity.this,AdviceActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    private void switchFragment(int viewId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*
        for ( Fragment frag : fragmentList ){
            fragmentTransaction.remove(frag);
        }
        */
        // fragmentTransaction.remove(fragmentManager.findFragmentById(viewId));
        fragmentTransaction.replace(viewId, fragment).commit();
        // fragmentList.add(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        // 如果先用 xml 定義 fragment , 在 switch 時 會殘留下來
        this.switchFragment(R.id.fragment , new CallFragment());

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        // 預設 3 個 以上的 bottom 按鈕 , 文字會隱藏 , 用  BottomNavigationViewHelper 避免此事發生 ( 至多5個不隱藏 )
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
