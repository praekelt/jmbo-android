package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import org.w3c.dom.Text;

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
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostDetailFragment extends Fragment {
    private TextView title;
    private TextView timeStamp;
    private TextView body;
    private ImageView image;

    private String uri;
    private String imageDir;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        imageDir = activity.getExternalFilesDir(null) + "/images";
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
        super.onStart();
//        // TODO all actionabr things from Main Activity
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        View v = inflater.inflate(R.layout.fragment_post_detail, container, false);

        title = (TextView) v.findViewById(R.id.title_text);
        timeStamp = (TextView) v.findViewById(R.id.text_time_stamp);
        body = (TextView) v.findViewById(R.id.body_text);
        image = (ImageView) v.findViewById(R.id.detail_image);

        return v;
    }


    public void onStart() {
        super.onStart();
        initDetail(uri);
    }

    /**
     * Assigns data to the views in the layout
     */
    private void initDetail(String uri) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.DEMO_API_BASE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);
        api.getDetail(uri.substring(1), new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                Log.d("Element String: ", String.valueOf(jsonElement));
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

    private void parseJson(JsonElement element) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object obj = JSONUtils.getDetailObject(element);

        Method m = obj.getClass().getMethod("getImage");
        Log.d("Image Url: ", String.valueOf(m.invoke(obj)));
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

        m = obj.getClass().getMethod("getContent");
        Log.d("Body: ", String.valueOf(m.invoke(obj)));
        body.setText(Html.fromHtml((String) (m.invoke(obj))));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onSaveInstanceState (Bundle outState) {
        //outState.putSerializable("viewData", (Serializable) item);
        super.onSaveInstanceState(outState);
    }

}
