package andrew.com.riko.www.webviewproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import andrew.com.riko.www.webviewproject.utils.BottomNavigationViewHelper;

public class BottomActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_call:
                    mTextMessage.setText(R.string.menu_call);
                    return true;
                case R.id.action_appointment:
                    mTextMessage.setText(R.string.menu_appointment);
                    return true;
                case R.id.action_history:
                    mTextMessage.setText(R.string.menu_history);
                    return true;
                case R.id.action_message:
                    mTextMessage.setText(R.string.menu_message);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
