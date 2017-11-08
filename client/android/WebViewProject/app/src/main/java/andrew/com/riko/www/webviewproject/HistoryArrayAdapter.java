package andrew.com.riko.www.webviewproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andrew.com.riko.www.webviewproject.model.History;


/**
 * Created by Test on 2017/11/4.
 */
public class HistoryArrayAdapter extends BaseAdapter {

    private Context context;
    private List<History> historys;

    public HistoryArrayAdapter(Context context,List<History> historys){
        this.context = context ;
        this.historys = historys;
    }

    @Override
    public int getCount() {
        return historys.size();
    }

    @Override
    public Object getItem(int position) {
        return historys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historys.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        History history = historys.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /* --->  不使用 convertView
        View rowView = null ;
        rowView = inflater.inflate(R.layout.list_view_item, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        ImageView headShot = (ImageView) rowView.findViewById(R.id.historyIV);
        title.setText(history.getTitle());
        description.setText(history.getDescription());
        headShot.setImageResource(history.getImageResourceId());
        return rowView ;
        */

        // 使用 convertView
        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            ImageView headShot = (ImageView) convertView.findViewById(R.id.historyIV);
            holder = new ViewHolder(title,description,headShot);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(history.getTitle());
        holder.description.setText(history.getDescription());

        Picasso.with(context)
                .load(history.getImageResourceId())
                .fit()
                .centerInside()  // call .centerInside() or .centerCrop() to avoid a stretched image
                .into(holder.headShot);

        // holder.headShot.setImageResource(history.getImageResourceId());

        // holder.headShot.setImageBitmap(); Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        return convertView ;
    }

    private class ViewHolder {

        private TextView title;
        private TextView description;
        private ImageView headShot;

        public ViewHolder(TextView title, TextView description, ImageView headShot) {
            this.title = title;
            this.description = description;
            this.headShot = headShot;
        }

    }



}
