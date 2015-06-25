package praekelt.weblistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by altus on 2015/03/19.
 */
public class FilterAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater;
    private String[] data;

    public FilterAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder {
        TextView typeText;
        ImageView typePic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String type = (String) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.block_filter, parent, false);

            viewHolder.typeText = (TextView) convertView.findViewById(R.id.type_text);
            viewHolder.typePic = (ImageView) convertView.findViewById(R.id.type_pic);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.typeText.setText(type);

        switch(type) {
            case "All":
                viewHolder.typePic.setImageResource(R.drawable.all);
                break;
            case "Music":
                viewHolder.typePic.setImageResource(R.drawable.music);
                break;
            case "News":
                viewHolder.typePic.setImageResource(R.drawable.news);
                break;
            case "Traffic":
                viewHolder.typePic.setImageResource(R.drawable.traffic);
                break;
            case "Weather":
                viewHolder.typePic.setImageResource(R.drawable.weather);
                break;
            case "Bulletin":
                viewHolder.typePic.setImageResource(R.drawable.news);
                break;
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public int getCount() {
        if(data == null) {
            return 0;
        } else {
            return data.length;
        }
    }
}
