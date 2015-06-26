package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.models.ModelBase;

public class ModelBaseDetailFragment extends Fragment {
        private String item;
        public fragmentCallback callback;
        private boolean firstLaunch = true;

        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                callback = (fragmentCallback) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
            }
        }

        public interface fragmentCallback {
            public void initView(String id);
        }

        public void onCreate(Bundle savedInstanceState) {
            if(savedInstanceState != null) {
                item = savedInstanceState.getString("viewData");
                firstLaunch = false;
            } else {
                firstLaunch = true;
            }
            super.onCreate(savedInstanceState);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onStart();
            // TODO all actionabr things from Main Activity
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            return inflater.inflate(R.layout.fragment_modelbase_detail, container, false);
        }


        public void onStart() {
            initDetail();
            super.onStart();
        }

        /**
         * Assigns data to the views in the layout
         */
        public void initDetail() {

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    // return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public void onSaveInstanceState (Bundle outState) {
            //outState.putSerializable("viewData", (Serializable) item);
            super.onSaveInstanceState(outState);
        }

}
