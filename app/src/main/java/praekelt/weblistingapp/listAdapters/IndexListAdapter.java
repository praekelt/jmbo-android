package praekelt.weblistingapp.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.utils.DateUtils;
import praekelt.weblistingapp.utils.ImageLoader;
import praekelt.weblistingapp.utils.StringUtils;
import praekelt.weblistingapp.utils.constants.Constants;

/**
 * Created by altus on 2015/01/12.
 * Set the values for the list row layouts
 */
public class IndexListAdapter extends BaseAdapter{//ArrayAdapter<ModelBase> {
    private LayoutInflater inflater;
    private ArrayList<ModelBase> items;
    private ImageLoader imageLoader;
    private String imageDir;

    public IndexListAdapter(Context context, ArrayList<ModelBase> contents) {
        this.items = contents;

        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(context);

        imageDir = context.getExternalFilesDir(null)+"/images";
    }

    /**
     * clears the list or initialises a new empty list
     * add all the data passed through from the loader
     * notify the list adapter that data has been changed to update list view
     * @param data the data in the form of an list of ModelBase passed from the loader
     */
    public void setData(List<ModelBase> data) {
        if (items != null) {
            items.clear();
        } else {
            items = new ArrayList<ModelBase>();
        }
        if (data != null) {
            items.addAll(data);
        }

        notifyDataSetChanged();
    }

    public void forceUpdate() {
        notifyDataSetChanged();
    }

    /**
     * View holder to be used to store recycled views' data
     */
    public class ViewHolder {
        int position;
        // Universal views
        ImageView imageView;
        TextView titleText;
        TextView timeStamp;

    }

    /**
     * Inflates and sets data for each row item in the list view.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    //TODO check if lists exists
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelBase indexItem = getItem(position);
        ViewHolder viewHolder;
        //int type = getItemViewType(position);
        // if current view does not exist, inflate new layout and add views to viewHolder
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.modelbase_list_item, parent, false);

            viewHolder.titleText = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.index_picture);
            viewHolder.imageView.setImageBitmap(null);
            viewHolder.timeStamp = (TextView) convertView.findViewById(R.id.text_time_stamp);
            viewHolder.position = position;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        // Setting Image
        viewHolder.imageView.setImageBitmap(null);
        imageLoader.displayImage(Constants.BASE_URL + indexItem.getImageDetailUrl().substring(1), viewHolder.imageView, StringUtils.uniqueMD5(indexItem.getImageDetailUrl()), imageDir);

        // Setting text
        viewHolder.titleText.setText(indexItem.getTitle());
        try {
            viewHolder.timeStamp.setText(DateUtils.getDate(indexItem.getPublishOn(), "yyyy-MM-dd hh:mm", "dd MMMM hh:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public int getCount() {
        return items.size();
    }

    public ModelBase getItem(int i) {
        return items.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public int getViewTypeCount() {
        return 1;
    }


    public int getItemViewType(int position) {
        return 0;
    }
}



