package praekelt.weblistingapp.ListView;

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
import praekelt.weblistingapp.Rest.Models.Item;

public class ModelBaseDetailFragment extends Fragment {
        private Item item;
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
                item = (Item) savedInstanceState.getSerializable("viewData");
                firstLaunch = false;
            } else {
                firstLaunch = true;
            }
            super.onCreate(savedInstanceState);
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onStart();
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            return inflater.inflate(R.layout.modelbase_detail_view, container, false);
        }


        public void onStart() {
            if(firstLaunch) {
                callback.initView("Game");
            } else {
                initDetail();
            }
            super.onStart();
        }

        public void setDetail(Item data) {
            item = data;
            initDetail();
        }

        /**
         * Assigns data to the views in the layout
         */
        public void initDetail() {
            WebView textBody = (WebView) getView().findViewById(R.id.text_body);

            String url = "http://192.168.1.114:8000" + (item.getPermalink());
            Log.d(url, "");

            //textBody.loadData("https://docs.djangoproject.com/en/1.8/ref/forms/widgets/", "text/html; charset=UTF-8", null);

            textBody.getSettings().setJavaScriptEnabled(true);
            textBody.loadUrl(url);
            //textBody.measure(100, 100);
            //textBody.getSettings().setUseWideViewPort(true);
            //textBody.getSettings().setLoadWithOverviewMode(true);
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
