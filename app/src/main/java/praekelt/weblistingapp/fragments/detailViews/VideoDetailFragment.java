package praekelt.weblistingapp.fragments.detailViews;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

import praekelt.weblistingapp.MainActivity;
import praekelt.weblistingapp.R;
import praekelt.weblistingapp.restfullApi.API;
import praekelt.weblistingapp.utils.DateUtils;
import praekelt.weblistingapp.utils.JSONUtils;
import praekelt.weblistingapp.utils.constants.Constants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VideoDetailFragment extends ModelBaseDetailFragment {

    private VideoView video;
    private MediaController vidControl;
    private ProgressDialog pDialog;
    int seekValue = 0;

    // Portrait specific
    private TextView title;
    private TextView timeStamp;
    private TextView content;

    private boolean landscape = false;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity.getResources().getConfiguration().orientation == Surface.ROTATION_180|| activity.getResources().getConfiguration().orientation == Surface.ROTATION_0) {
            ActionBar actionBar = activity.getActionBar();
            actionBar.hide();
            Log.d("Orient: ", "Landscape");
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            landscape = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null) {
            seekValue = savedInstanceState.getInt("vidPosition");
        } else {

        }
        vidControl = new MediaController(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_video_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Don't call super due to title and timestamp not always being available depending on orientation
        if(!landscape) {
            Log.d("Orient: ", "Portrait");
            title = (TextView) view.findViewById(R.id.title_text);
            timeStamp = (TextView) view.findViewById(R.id.text_time_stamp);
            content = (TextView) view.findViewById(R.id.content_text);
        }

        video = (VideoView) view.findViewById(R.id.video_view);

        video.setMediaController(vidControl);

        displayDialog();
    }

    private void displayDialog() {
        pDialog = new ProgressDialog(getActivity());

        pDialog.setTitle(R.string.fetch_stream_title);
        pDialog.setMessage(getString(R.string.buffering));

        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });

        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void setData(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = obj.getClass().getMethod("getStream");
        loadStream((String) m.invoke(obj));
        Log.d("Stream URL: ", (String) m.invoke(obj));

        if(!landscape) {
            m = obj.getClass().getMethod("getTitle");
            title.setText((String) m.invoke(obj));
            Log.d("Title: ", (String) m.invoke(obj));

            m = obj.getClass().getMethod("getPublishOn");
            Log.d("publishOn: ", String.valueOf(m.invoke(obj)));
            try {
                timeStamp.setText(DateUtils.getDate((String) (m.invoke(obj)), "yyyy-MM-dd hh:mm", "dd MMMM hh:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            m = obj.getClass().getMethod("getContent");
            content.setText(Html.fromHtml((String) m.invoke(obj)));
            Log.d("Content: ", (String) m.invoke(obj));
        }
    }

    private void loadStream(String path) {
        Uri vidUri = Uri.parse(path);
        video.setMediaController(vidControl);
        video.setVideoURI(vidUri);

        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                if (!(seekValue == 0)) {
                    Log.d("Has seek to: ", "VideoView");
                    video.seekTo(seekValue);

                } else {
                    Log.d("No seek to: ", "VideoView");
                    video.seekTo(100);
                }
                video.start();
            }
        });

        vidControl.setAnchorView(video);

        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                pDialog.dismiss();
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error));
                getActivity().onBackPressed();

                return true;
            }
        });

        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (video.isPlaying()) {
                        if (vidControl.isShowing()) {
                            video.pause();
                        }
                        vidControl.show();
                        Log.d("Video: ", "Paused");
                    } else {
                        if (vidControl.isShowing()) {
                            video.start();
                        }
                        vidControl.show();
                        Log.d("Video: ", "Playing");
                    }
                }
                return true;
            }
        });
    }

    public void onPause() {
        super.onPause();
        video.pause();

        seekValue = video.getCurrentPosition();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("vidPosition", seekValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
