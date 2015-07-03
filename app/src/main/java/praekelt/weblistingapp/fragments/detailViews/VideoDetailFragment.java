package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.MainActivity;
import praekelt.weblistingapp.R;

// TODO VideoView network calls seem to block ui thread, fix: currently unknown
public class VideoDetailFragment extends ModelBaseDetailFragment implements MediaPlayer.OnErrorListener, View.OnTouchListener {
    private TextView content;
    private TextView title;
    private VideoView video;
    private ProgressDialog pDialog;
    private MediaController vidControl;
    private int seekValue = 0;

    private boolean landscape;

    public VideoDetailFragment() {
        // Required empty constructor
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(savedInstanceState != null) {
            seekValue = savedInstanceState.getInt("seekValue");
        }

        return inflater.inflate(R.layout.fragment_video_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int orient = getActivity().getResources().getConfiguration().orientation;

        if(orient == Surface.ROTATION_180 || orient == Surface.ROTATION_0) {
            //Log.d("Orient: ", "Landscape");
            ((MainActivity)getActivity()).fullscreenMethod();

            landscape = true;
        } else {
            title = (TextView) view.findViewById(R.id.title_text);
            content = (TextView) view.findViewById(R.id.content_text);
        }

        video = (VideoView) view.findViewById(R.id.video_view);

        vidControl = new MediaController(getActivity());

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

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       //ORIENT

        video.setMediaController(vidControl);
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

            m = obj.getClass().getMethod("getContent");
            content.setText(Html.fromHtml((String) (m.invoke(obj))));
        }
    }

    private void loadStream(String path) {
        video.setVideoURI(Uri.parse(path));

        video.requestFocus();
        video.seekTo(seekValue);
        video.start();

        vidControl.setAnchorView(video);

        pDialog.dismiss();
        video.setOnTouchListener(this);

        video.setOnErrorListener(this);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        pDialog.dismiss();
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error_TIME_OUT));
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error_UNSUPPORTED));
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error_MALFORMED));
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error_SERVER_DIED));
                break;
            default:
                ((MainActivity) getActivity()).alertDialogue(getString(R.string.video_error));
                break;
        }

        getActivity().onBackPressed();

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (video.isPlaying()) {
                if (vidControl.isShowing()) {
                    video.pause();
                }
                vidControl.show();
                Log.i("Video: ", "Paused");
            } else {
                if (vidControl.isShowing()) {
                    video.start();
                }
                vidControl.show();
                Log.i("Video: ", "Playing");
            }
        }
        return true;
    }

    @Override
    public void onPause() {
        Log.d("Method Start: ", "onPause()" + System.currentTimeMillis());
        video.pause();
        seekValue = video.getCurrentPosition();

        Log.d("Method Call: ", ".    suspend()" + System.currentTimeMillis());
        video.suspend();
        Log.d("Method Call: ", ".    suspend()" + System.currentTimeMillis());

        super.onPause();
        Log.d("SeekValue: ", String.valueOf(seekValue));

        Log.d("Method Stop: ", "onPause()" + System.currentTimeMillis());
    }

    public void onStop() {
        Log.d("Method Start: ", "onStop()" + System.currentTimeMillis());
        super.onStop();
        Log.d("Method Stop: ", "onStop()" + System.currentTimeMillis());
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.d("Method Start: ", "onSaveInstanceState()" + System.currentTimeMillis());
        outState.putInt("seekValue", seekValue);
        super.onSaveInstanceState(outState);
        Log.d("Method Stop: ", "onSaveInstanceState()" + System.currentTimeMillis());
    }

    public void onDestroyView() {
        Log.d("Method Start: ", "onDestroyView()" + System.currentTimeMillis());
        super.onDestroyView();
        Log.d("Method Stop: ", "onDestroyView()" + System.currentTimeMillis());
    }

    public void onDestroy() {
        Log.d("Method Start: ", "onDestroy()" + System.currentTimeMillis());
        super.onDestroy();
        Log.d("Method Stop: ", "onDestroy()" + System.currentTimeMillis());
    }

    public void onDetach() {
        Log.d("Method Start: ", "onDetach()" + System.currentTimeMillis());
        //video.stopPlayback();
        super.onDetach();
        Log.d("Method Stop: ", "onDetach()" + System.currentTimeMillis());
    }

}