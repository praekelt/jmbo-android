package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.restfullApi.API;
import praekelt.weblistingapp.utils.JSONUtils;
import praekelt.weblistingapp.utils.constants.Constants;
import praekelt.weblistingapp.utils.constants.Registry;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Path;

public class VideoDetailFragment extends Fragment {

    Button playPause;
    VideoView video;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String uri = bundle.getString("uri");
            Log.d("Video Uri: ", uri);
            initDetail(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_detail, container, false);
        playPause = (Button) v.findViewById(R.id.videoToggle);
        playPause.getBackground().setColorFilter(0xffd45cc0, PorterDuff.Mode.MULTIPLY);

        video = (VideoView) v.findViewById(R.id.video_view);
//
//        playPause.setClickable(false);
//        playPause.getBackground().setColorFilter(0xffc4c4c4, PorterDuff.Mode.MULTIPLY);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean on = ((ToggleButton) view).isChecked();
                if (on) {
                    play();
                    Log.i("Video State: ", "Playing");
                } else {
                    stop();
                    Log.i("Video State: ", "Stopped");
                }
            }
        });

        return v;
    }

    private void initDetail(String uri) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.DEMO_API_BASE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);
        api.getVideoStream(uri.substring(1), new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                Log.d("Element String: ", String.valueOf(jsonElement));

                try {
                    parseJson(jsonElement);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
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

    private void parseJson(JsonElement element) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, java.lang.InstantiationException, IllegalAccessException {
        Object obj = JSONUtils.getDetailObject(element);
        Method m = obj.getClass().getDeclaredMethod("getStream");
        loadStream((String) m.invoke(obj));
        Log.d("Stream URL: ", (String) m.invoke(obj));
    }

    private void loadStream(String path) {
        video.setVideoURI(Uri.parse(path));
//
//        playPause.setClickable(true);
//        playPause.getBackground().setColorFilter(0xffd45cc0, PorterDuff.Mode.MULTIPLY);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void play() {
        video.start();
    }

    private void stop() {
        //video.stopPlayback();
        video.pause();
    }
}
