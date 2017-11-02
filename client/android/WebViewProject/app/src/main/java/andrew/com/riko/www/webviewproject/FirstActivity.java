package andrew.com.riko.www.webviewproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button tokenBTN = (Button) findViewById(R.id.goToGetTokenPage);
        Button messageBTN = (Button) findViewById(R.id.goToSendMessagePage);
        Button loginBTN = (Button) findViewById(R.id.goToLoginPage);
        Button nativeBTN = (Button) findViewById(R.id.goToBNP);
        Button htmlBTN = (Button) findViewById(R.id.goToWebViewPage);
        Button picBTN = (Button) findViewById(R.id.goToPicPage);

        tokenBTN.setOnClickListener(clickListener);
        messageBTN.setOnClickListener(clickListener);
        loginBTN.setOnClickListener(clickListener);
        nativeBTN.setOnClickListener(clickListener);
        htmlBTN.setOnClickListener(clickListener);
        picBTN.setOnClickListener(clickListener);



    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            Map<Integer,Class> map = new HashMap<>();
            map.put(R.id.goToGetTokenPage,GetTokenActivity.class);
            map.put(R.id.goToSendMessagePage,FirstActivity.class);
            map.put(R.id.goToLoginPage,LoginActivity.class);
            map.put(R.id.goToBNP,BottomActivity.class);
            map.put(R.id.goToWebViewPage,HtmlActivity.class);
            map.put(R.id.goToPicPage,PictureActivity.class);

            Class targetActivity = map.get(v.getId());
            if ( targetActivity != null ){
                Intent intent = new Intent(FirstActivity.this, targetActivity );
                if ( intent != null ) startActivity(intent);
            }

        }
    };

}
