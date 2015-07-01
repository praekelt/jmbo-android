package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.restfullApi.API;
import praekelt.weblistingapp.utils.DateUtils;
import praekelt.weblistingapp.utils.ImageLoader;
import praekelt.weblistingapp.utils.JSONUtils;
import praekelt.weblistingapp.utils.StringUtils;
import praekelt.weblistingapp.utils.constants.Constants;
import praekelt.weblistingapp.utils.fileSystemUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ModelBaseDetailFragment extends Fragment {
    private TextView title;
    private TextView timeStamp;
    private ImageView image;

    private String uri;
    private String imageDir;

    public ModelBaseDetailFragment() {
        // Required empty public constructor
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        imageDir = activity.getExternalFilesDir(null) + "/images";
        fileSystemUtils.checkDirectory(imageDir);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            uri = bundle.getString("uri");
            Log.d("Uri: ", uri);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        // TODO ActionBar set in MainActivity

        return inflater.inflate(R.layout.fragment_modelbase_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView) view.findViewById(R.id.title_text);
        timeStamp = (TextView) view.findViewById(R.id.text_time_stamp);
        image = (ImageView) view.findViewById(R.id.detail_image);
    }


    public void onStart() {
        super.onStart();
        accessAPI(uri);
    }

    /**
     * Assigns data to the views in the layout
     */
    private void accessAPI(String uri) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.DEMO_API_BASE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);
        api.getDetail(uri.substring(1), new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                // TODO remove or uncomment if issues with incoming maessage
//                Log.d("Element String: ", String.valueOf(jsonElement));
                try {
                    parseJson(jsonElement);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void parseJson(JsonElement element) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object obj = JSONUtils.getDetailObject(element);
        setData(obj);
    }

    public void setData(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = obj.getClass().getMethod("getImageDetailUrl");
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.displayImage(Constants.DEMO_API_BASE + String.valueOf(m.invoke(obj)).substring(1), image, StringUtils.uniqueMD5(String.valueOf(m.invoke(obj))), imageDir);

        m = obj.getClass().getMethod("getTitle");
        Log.d("Title: ", String.valueOf(m.invoke(obj)));
        title.setText((String) (m.invoke(obj)));

        m = obj.getClass().getMethod("getPublishOn");
        Log.d("publishOn: ", String.valueOf(m.invoke(obj)));
        try {
            timeStamp.setText(DateUtils.getDate((String) (m.invoke(obj)), "yyyy-MM-dd hh:mm", "dd MMMM hh:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // TODO

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
