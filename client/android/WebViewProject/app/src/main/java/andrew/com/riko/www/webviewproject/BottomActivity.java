package andrew.com.riko.www.webviewproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    BottomActivity.this.switchToFragment(R.id.fragment,new CallFragment());
                    return true;
                case R.id.action_appointment:
                    mTextMessage.setText(R.string.menu_appointment);
                    BottomActivity.this.switchToFragment(R.id.fragment,new AppointmentFragment());
                    return true;
                case R.id.action_history:
                    mTextMessage.setText(R.string.menu_history);
                    BottomActivity.this.switchToFragment(R.id.fragment,new HistoryFragment());
                    return true;
                case R.id.action_message:
                    mTextMessage.setText(R.string.menu_message);
                    BottomActivity.this.switchToFragment(R.id.fragment,new MessageFragment());
                    return true;
            }
            return false;
        }

    };

    private void switchToFragment(int viewId,Fragment fragment) {
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
        this.switchToFragment(R.id.fragment , new CallFragment());

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
