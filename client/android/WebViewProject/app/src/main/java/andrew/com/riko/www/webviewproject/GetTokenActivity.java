package andrew.com.riko.www.webviewproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import andrew.com.riko.www.webviewproject.properties.KeyName;

/**
 * Created by Test on 2017/8/13.
 */
public class GetTokenActivity extends Activity {

    EditText urlEditText ;
    EditText roomNameEditText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.get_token_layout);

        urlEditText = (EditText) super.findViewById(R.id.inputURL);
        roomNameEditText = (EditText) super.findViewById(R.id.roomName);
    }


    public void connectClick(View v){

        String url = urlEditText.getText().toString();
        String roomName = roomNameEditText.getText().toString();

        if( url == null ){
            Toast.makeText( this , "請輸入連線URL" , Toast.LENGTH_SHORT ).show();
        }else {
            Intent intent = new Intent(this,HtmlActivity.class);
            intent.putExtra(KeyName.SERVER_URL,url);
            intent.putExtra(KeyName.ROOM_NAME,roomName);
            startActivity(intent);
        }


    }

}
