package andrew.com.riko.www.doctorapplication;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.Date;

import andrew.com.riko.www.doctorapplication.model.Appointment;
import andrew.com.riko.www.doctorapplication.properties.Status;
import andrew.com.riko.www.yiyolibrary.utils.DateUtils;
import andrew.com.riko.www.yiyolibrary.utils.StringUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppointmentActivity extends AppCompatActivity {

    private AppointmentActivity.ViewHolder viewHolder ;
    private int missionId ;
    private class ViewHolder{

        private TextView identityNo;
        private TextView birthday;
        private TextView description;
        private TextView name;
        private Button success;
        private Button fail;

        public ViewHolder(TextView identityNo, TextView birthday, TextView description, TextView name, Button success, Button fail) {
            this.identityNo = identityNo;
            this.birthday = birthday;
            this.description = description;
            this.name = name;
            this.success = success;
            this.fail = fail;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        getSupportActionBar().setTitle("掛號");

        TextView identityNoTV = (TextView) findViewById(R.id.identityNoTV);
        TextView birthdayTV = (TextView) findViewById(R.id.birthdayTV);
        TextView nameTV = (TextView) findViewById(R.id.nameTV);
        TextView descriptionTV = (TextView) findViewById(R.id.description);
        Button successBTN = (Button) findViewById(R.id.successBTN);
        Button failBTN = (Button) findViewById(R.id.failBTN);

        String missionType = getIntent().getStringExtra("mission_type");
        String requesterName = getIntent().getStringExtra("name");
        String issueAt = getIntent().getStringExtra("issue_at");
        String description = getIntent().getStringExtra("description");
        final String missionId = getIntent().getStringExtra("mission_id");
        String parentId = getIntent().getStringExtra("parent_id");

        if ( !StringUtils.isBlank(missionId) ) this.missionId = Integer.valueOf(missionId);
        if ( !StringUtils.isBlank(requesterName) ) nameTV.setText(requesterName);
        if ( !StringUtils.isBlank(description) ) descriptionTV.setText(requesterName);

        successBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( AppointmentActivity.this.missionId != 0) AppointmentActivity.this.patchToCallCenter(Status.SUCCESS);
            }
        });

        failBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( AppointmentActivity.this.missionId != 0) AppointmentActivity.this.patchToCallCenter(Status.FAIL);
            }
        });

        viewHolder = new ViewHolder(identityNoTV,birthdayTV,descriptionTV,nameTV,successBTN,failBTN);
    }

    private void patchToCallCenter(final Status status){

        Thread thread = new Thread(new Runnable() {

               private FormBody makeFormBody() {

                   String description = viewHolder.description.getText().toString();
                   String fcmToken = FirebaseInstanceId.getInstance().getToken();

                   // formBody 已使用  Content-Type = application/x-www-form-urlencoded
                   FormBody formBody = new FormBody.Builder()
                           .add("status_id", status.getId()+"") // 狀態任務 拒絕給 X , 接受給 Y
                           //   .add("requester_name", "賴珠珠") // => 改成 name ?
                           .add("status_name", status.getName())  //  拒絕給 拒絕任務 , 接受給 接受任務
                           .add("suggestion", "此欄位為醫師填寫欄位")
                           .add("took_at", DateUtils.sdDateTime.format(new Date())) // 接受 or 拒絕任務的時間
                           .add("fcmToken", fcmToken)
                           .build();

                   return formBody;
            }

            @Override
            public void run() {

                String url = "https://www.yoecare.com/missions/"+missionId;

                OkHttpClient client = new OkHttpClient();
                FormBody formBody = this.makeFormBody();
                Request request =
                        new Request.Builder()
                        .url(url)
                        .patch(formBody)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();

                    if ( response.code() == 200 ){

                        AppointmentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppointmentActivity.this.showDialog("您的訊息已發送成功!");
                            }
                        });


                    }else {

                        AppointmentActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppointmentActivity.this.showDialog("訊息發送失敗,請聯繫Call Center");
                            }
                        });

                    }

                } catch (IOException e) {
                    Log.e("In Appoint Activity","patchToCallCenter error");
                }

            }
        });
        thread.start();


    }

    private void showDialog(String msg){

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("訊息發送")
                .setMessage(msg);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
