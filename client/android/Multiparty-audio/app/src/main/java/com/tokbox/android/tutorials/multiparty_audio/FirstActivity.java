package com.tokbox.android.tutorials.multiparty_audio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Test on 2017/8/13.
 */

public class FirstActivity extends Activity {

    EditText urlEditText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.first_layout);

        urlEditText = (EditText) super.findViewById(R.id.inputURL);


    }


    public void connectClick(View v){

        String url = urlEditText.getText().toString();

        if( url == null ){
            Toast.makeText( this , "請輸入連線URL" , Toast.LENGTH_SHORT ).show();
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("CHAT_SERVER_URL",url);
            startActivity(intent);
        }


    }

}
