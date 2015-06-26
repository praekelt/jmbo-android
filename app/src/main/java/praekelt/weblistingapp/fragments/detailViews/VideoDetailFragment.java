package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.restfullApi.API;
import praekelt.weblistingapp.utils.JSONUtils;
import praekelt.weblistingapp.utils.constants.Constants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VideoDetailFragment extends Fragment {

    VideoView video;
    MediaController vidControl;
    ProgressDialog pDialog;
    int seekValue = 0;

    private String uri;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null) {
            seekValue = savedInstanceState.getInt("vidPosition");
        } else {

        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uri = bundle.getString("uri");
            Log.d("Video Uri: ", uri);
        }

        vidControl = new MediaController(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_detail, container, false);

        video = (VideoView) v.findViewById(R.id.video_view);


        video.setMediaController(vidControl);

        pDialog = new ProgressDialog(getActivity());

        pDialog.setTitle(R.string.fetch_stream_title);
        pDialog.setMessage(getString(R.string.buffering));

        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();

        return v;
    }

    public void onStart() {
        super.onStart();
        initDetail(uri);
    }

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
        Method m = obj.getClass().getMethod("getStream");
        loadStream((String) m.invoke(obj));
        Log.d("Stream URL: ", (String) m.invoke(obj));
    }

    private void loadStream(String path) {
        vidControl.setAnchorView(video);
        Uri vidUri = Uri.parse(path);
        video.setMediaController(vidControl);
        video.setVideoURI(vidUri);

        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                if(!(seekValue == 0)) {
                    video.seekTo(seekValue);
                }
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        seekValue = video.getCurrentPosition();
        outState.putInt("vidPosition", seekValue);
        super.onSaveInstanceState(outState);
    }


        @Override
    public void onDetach() {
        super.onDetach();
    }
}
