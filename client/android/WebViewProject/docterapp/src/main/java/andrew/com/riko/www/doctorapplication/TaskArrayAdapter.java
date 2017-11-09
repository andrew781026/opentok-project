package andrew.com.riko.www.doctorapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import andrew.com.riko.www.doctorapplication.model.Task;
import andrew.com.riko.www.doctorapplication.properties.KeyName;
import andrew.com.riko.www.doctorapplication.properties.Status;
import andrew.com.riko.www.doctorapplication.utils.DialogUtils;
import andrew.com.riko.www.yiyolibrary.utils.DateUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Test on 2017/11/4.
 */
public class TaskArrayAdapter extends BaseAdapter {

    private Activity context;
    private List<Task> tasks;

    public TaskArrayAdapter(Activity context, List<Task> tasks){
        this.context = context ;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.indexOf(getItem(position));
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final Task task = tasks.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        // 使用 convertView
        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView age = (TextView) convertView.findViewById(R.id.age);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            ImageView headShot = (ImageView) convertView.findViewById(R.id.headShot);
            Button accept = (Button) convertView.findViewById(R.id.acceptBTN);
            Button deny = (Button) convertView.findViewById(R.id.denyBTN);

            holder = new ViewHolder(title, age, name, description, headShot,accept,deny);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(task.getTitle());
        holder.name.setText(task.getName());
        holder.age.setText(task.getAge()+" 歲");
        holder.description.setText(task.getDescription());
        if ( task.getImageResourceId() != 0 ) holder.headShot.setImageResource(task.getImageResourceId());

        // 要做 view reuse , 有可能 convertView != null , 所以需要在此才做 click Listener 的設置
        holder.accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("In Appoint Activity","accept click");
                patchToCallCenter(Status.ACCEPT,task);
            }
        });

        holder.deny.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("In Appoint Activity","deny click");
                patchToCallCenter(Status.DENY,task);
            }
        });

        return convertView ;

    }

    private void patchToCallCenter(final Status status, final Task task){

        Thread thread = new Thread(new Runnable() {

            private FormBody makeFormBody() {

                String fcmToken = FirebaseInstanceId.getInstance().getToken();
                String took_at = DateUtils.sdDate.format(new Date()) ;

                Log.i("In Appoint Activity","took_at="+took_at);
                // formBody 已使用  Content-Type = application/x-www-form-urlencoded
                FormBody formBody = new FormBody.Builder()
                        .addEncoded("status_id", status.getId()+"") // 狀態任務 拒絕給 X , 接受給 Y
                        //   .add("requester_name", "賴珠珠") // => 改成 name ?
                        .add("status_name", status.getName())  //  拒絕給 拒絕任務 , 接受給 接受任務
                        .add("suggestion", "此欄位為醫師填寫欄位")
                        .add("took_at", took_at) // 接受 or 拒絕任務的時間
                        .add("fcmToken", fcmToken)
                        .build();

                return formBody;
            }

            @Override
            public void run() {

                String url = "https://www.yoecare.com/missions/"+task.getMissionId();

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
                    Log.i("In Appoint Activity",response.body().string());

                    if ( response.code() == 200 ){
                        if ( Status.DENY == status ) return ;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startAppointmentActivity(task);
                            }
                        });
                    }else {
                        DialogUtils.showSimpleInfoDialog(context,"訊息發送","訊息發送失敗,請聯繫Call Center");
                    }

                } catch (IOException e) {
                    Log.e("In Appoint Activity","patchToCallCenter error");
                    DialogUtils.showSimpleInfoDialog(context,"訊息發送","連線到Call Center失敗,請打電話通知管理人員");
                }

            }
        });
        thread.start();


    }

    private void startAppointmentActivity(Task task){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyName.TASK,task);
        Intent intent = null ;
        if ( "諮詢請求".equalsIgnoreCase(task.getTitle()) || "諮詢".equalsIgnoreCase(task.getTitle()) ){
            intent = new Intent(context,AdviceActivity.class);
        }else if ( "掛號請求".equalsIgnoreCase(task.getTitle()) || "掛號".equalsIgnoreCase(task.getTitle()) ){
            intent = new Intent(context,AppointmentActivity.class);
        }else if ( "陪同請求".equalsIgnoreCase(task.getTitle()) ){
            intent = new Intent(context,AdviceActivity.class);
        }

        if ( intent != null ){
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    private class ViewHolder {

        private TextView title;
        private TextView age;
        private TextView name;
        private TextView description;
        private ImageView headShot;
        private Button accept ;
        private Button deny ;

        public ViewHolder() {}

        public ViewHolder(TextView title, TextView age, TextView name,
                          TextView description, ImageView headShot,
                          Button accept,Button deny) {
            this.title = title;
            this.age = age;
            this.name = name;
            this.description = description;
            this.headShot = headShot;
            this.accept = accept;
            this.deny = deny;
        }
    }

}
