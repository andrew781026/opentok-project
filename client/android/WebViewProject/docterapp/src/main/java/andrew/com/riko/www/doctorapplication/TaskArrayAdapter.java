package andrew.com.riko.www.doctorapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import andrew.com.riko.www.doctorapplication.model.Task;
import andrew.com.riko.www.doctorapplication.properties.KeyName;


/**
 * Created by Test on 2017/11/4.
 */
public class TaskArrayAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasks;

    public TaskArrayAdapter(Context context, List<Task> tasks){
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

            holder = new ViewHolder(title, age, name, description, headShot,accept);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(task.getTitle());
        holder.name.setText(task.getName());
        holder.age.setText(task.getAge()+" 歲");
        holder.description.setText(task.getDescription());
        holder.headShot.setImageResource(task.getImageResourceId());

        // 要做 view reuse , 有可能 convertView != null , 所以需要在此才做 click Listener 的設置
        holder.accept.setOnClickListener(new View.OnClickListener() {

            private void startAppointmentActivity(){
                Bundle bundle = new Bundle();
                bundle.putSerializable(KeyName.TASK,task);
                Intent intent = null ;
                if ( "諮詢請求".equalsIgnoreCase(task.getTitle()) ){
                    intent = new Intent(context,AdviceActivity.class);
                }else if ( "掛號請求".equalsIgnoreCase(task.getTitle()) ){
                    intent = new Intent(context,AppointmentActivity.class);
                }else if ( "陪同請求".equalsIgnoreCase(task.getTitle()) ){
                    intent = new Intent(context,AdviceActivity.class);
                }

                if ( intent != null ){
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }

            }

            @Override
            public void onClick(View v) {
                this.startAppointmentActivity();
                // Intent intent = new Intent(context,AdviceActivity.class);
                // context.startActivity(intent);
            }
        });
        return convertView ;

    }

    private class ViewHolder {

        private TextView title;
        private TextView age;
        private TextView name;
        private TextView description;
        private ImageView headShot;
        private Button accept ;

        public ViewHolder() {}

        public ViewHolder(TextView title, TextView age, TextView name,
                          TextView description, ImageView headShot, Button accept) {
            this.title = title;
            this.age = age;
            this.name = name;
            this.description = description;
            this.headShot = headShot;
            this.accept = accept;
        }
    }



}
