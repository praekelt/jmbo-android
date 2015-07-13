package praekelt.weblistingapp.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
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

    private Parcelable state;

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
        void inflateDetailView(ModelBase item, String id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, java.lang.InstantiationException;
        void getList();
        public String getFilter();
    }

    /**
     * Initialises the list view
     * and initialises the loader
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assign adapter to ListView
        listAdapter = new IndexListAdapter(getActivity().getApplicationContext(), new ArrayList<ModelBase>());
        // Fragment needs to extend the ListFragment
        setListAdapter(listAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Inflate the layout
        Log.i("List View:", " Inflating");
        return inflater.inflate(R.layout.fragment_index_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null) {
            //Log.d("State: ", "savedState");
            state = savedInstanceState.getParcelable("state");
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callback.getList();
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    /**
     * Saves the current list position out to the HelperFragment fragment via the Main Activity
     */
    public void onPause() {
        state = getListView().onSaveInstanceState();
        super.onPause();
    }

    public void onSaveInstanceState(Bundle outState) {
        // TODO state change in Detail nullifies state
        if (state != null) {
            outState.putParcelable("state", state);
            Log.d("STATE-OUT: ", state.toString());
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Notifies the main activity which view it needs to inflate, based on the item selected from the list
     * @param list
     * @param view
     * @param listPosition
     * @param id
     */
    public void onListItemClick(ListView list, View view, int listPosition, long id) {
        // Send Data of Clicked Item
        ModelBase item = listAdapter.getItem(listPosition);
        try {
            callback.inflateDetailView(item, String.valueOf(id));
        } catch (NoSuchMethodException e) {
            Log.e("Error: ", "No such method found in class");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    private List<ModelBase> data;

    public void setListData(List<ModelBase> data) {
        this.data = null;
        this.data = data;
        populateList();
        setPosition();
    }

    private void populateList() {
        listAdapter.setData(data);;
    }

    private void setPosition() {
        if(state != null) {
            //Log.d("State: ", "Restored");
            Log.d("STATE: ", state.toString());
            getListView().onRestoreInstanceState(state);
        }
    }
}
