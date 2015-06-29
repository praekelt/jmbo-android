package praekelt.weblistingapp.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import praekelt.weblistingapp.listAdapters.IndexListAdapter;
import praekelt.weblistingapp.R;
import praekelt.weblistingapp.models.ModelBase;

/**
 * Created by altus on 2015/03/03.
 *
 * Populates the list view with data from the loader
 */
public class IndexListFragment extends ListFragment {

    private IndexListAdapter listAdapter;
    public listCallbacks callback;
    private Parcelable position = null;

    /**
     * Instantiates the callback method
     * @param activity
     */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (listCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface listCallbacks {
        public void inflateDetailView(ModelBase item, String id);
        public void setPosition(Bundle bundle);
        public void updateList();
        public String getFilter();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Inflate the layout
        Log.i("List View:", " Inflating");
        return inflater.inflate(R.layout.fragment_index_list, container, false);
    }

    /**
     * Initialises the list view
     * and initialises the loader
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void onStart() {
        super.onStart();
        callback.updateList();
    }

    public void onResume() {
        super.onResume();
        updatePosition(position);
    }

    /**
     * Saves the current list position out to the HelperFragment fragment via the Main Activity
     */
    public void onPause() {
        Bundle bundle = new Bundle();
        position = getListView().onSaveInstanceState();
        bundle.putParcelable("listPosition", position);

        callback.setPosition(bundle);
        super.onPause();
    }

    /**
     * Notifies the main activity which view it needs to inflate, based on the item selected from the list
     * @param list
     * @param v
     * @param listPosition
     * @param id
     */
    public void onListItemClick(ListView list, View v, int listPosition, long id) {
        // Send Data of Clicked Item
        ModelBase item = listAdapter.getItem(listPosition);
        callback.inflateDetailView(item, "Game");
    }

    /**
     * Assigns the list Adapter
     */
    private void init() {
        // Inflates the main layout
        Log.i("Initialising List", "True");
        // Assign adapter to ListView
        listAdapter = new IndexListAdapter(getActivity().getApplicationContext(), new ArrayList<ModelBase>());

        // Fragment needs to extend the ListFragment
        setListAdapter(listAdapter);
    }

    public void setListData(List<ModelBase> data) {
            listAdapter.setData(data);
    }

    /**
     * Main Activity sends the list position and sets a boolean flag that allows the position to be updated
     * @param position
     */
    public void refocused(Bundle position) {
        this.position =  position.getParcelable("listPosition");
    }

    /**
     * List position is updated if the boolean flag is true
     * @param position
     */
    public void updatePosition(Parcelable position) {
        if(position != null) {
            Log.d("UPDATED POSITION: ", String.valueOf(position));
            getListView().onRestoreInstanceState(position);
        }
    }
}
