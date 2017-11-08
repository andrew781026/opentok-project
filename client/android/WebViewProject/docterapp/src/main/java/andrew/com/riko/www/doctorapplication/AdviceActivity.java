package andrew.com.riko.www.doctorapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import andrew.com.riko.www.doctorapplication.model.Task;
import andrew.com.riko.www.doctorapplication.properties.KeyName;

public class AdviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("fcmToken=",refreshedToken);

        TextView title = (TextView) findViewById(R.id.title);
        ImageView headShot = (ImageView) findViewById(R.id.headShot);
        TextView description = (TextView) findViewById(R.id.description);
        TextView name = (TextView) findViewById(R.id.name);
        Button button = (Button) findViewById(R.id.gotoVideoChat);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.andrew.videoChat");
                startActivity(intent);
            }
        });

        Task task = (Task) getIntent().getSerializableExtra(KeyName.TASK);
        if ( task != null ){
            title.setText(task.getTitle());
            headShot.setImageDrawable(getResources().getDrawable( task.getImageResourceId() ));
            description.setText(task.getDescription());
            name.setText(task.getName());
        }

    }


}
