package praekelt.weblistingapp.fragments.detailViews;

import android.media.AudioFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;

import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import praekelt.weblistingapp.MainActivity;
import praekelt.weblistingapp.R;
import praekelt.weblistingapp.utils.mediaPlayer.HlsRendererBuilder;
import praekelt.weblistingapp.utils.mediaPlayer.Player;

public class ExoVideoFragment extends ModelBaseDetailFragment implements Player.Listener, Player.TextListener, Player.Id3MetadataListener, SurfaceHolder.Callback {
    private com.google.android.exoplayer.VideoSurfaceView surface;
    private View shutterView;
    private ProgressBar spinner;
    private View root;

    private Player player;
    private MediaController mediaController;
    private boolean playerNeedsPrepare = true;
    private  HlsRendererBuilder builder;
    private long playerPosition = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong("seekValue");
        }

        return inflater.inflate(R.layout.exo_test, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).fullscreenMethod();

        surface = (com.google.android.exoplayer.VideoSurfaceView) view.findViewById(R.id.surface_view);
        surface.getHolder().addCallback(this);

        shutterView = view.findViewById(R.id.shutter);

        spinner = (ProgressBar) view.findViewById(R.id.spinner);

        root = view.findViewById(R.id.root);

        // Hide show media controls as view is tapped
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility();
                    Log.d("Toggle Vis: ", "MediaController Toggle");
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //view.performClick();
                    Log.d("Perform click: ", "On Root");
                }
                return true;
            }
        });

        mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(root);
    }


        @Override
    public void setData(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = obj.getClass().getMethod("getStream");

        String userAgent = Util.getUserAgent(getActivity(), "ExoPlayerDemo");
        AudioCapabilities audioCapabilities = new AudioCapabilities(new int[]{AudioFormat.ENCODING_PCM_8BIT}, 2);

        builder = new HlsRendererBuilder(getActivity(), userAgent, ((String) m.invoke(obj)), null, audioCapabilities);

        preparePlayer();
    }

    private void preparePlayer() {
        if (player == null) {
            player = new Player(builder);
            player.addListener(this);
            player.setTextListener(this);
            player.setMetadataListener(this);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;
            mediaController.setMediaPlayer(player.getPlayerControl());
            mediaController.setEnabled(true);
            //eventLogger = new EventLogger();
            //eventLogger.startSession();
            //player.addListener(eventLogger);
            //player.setInfoListener(eventLogger);
            //player.setInternalErrorListener(eventLogger);
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
            //updateButtonVisibilities();
        }
        player.setSurface(surface.getHolder().getSurface());
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        //if (!enableBackgroundAudio) {
            releasePlayer();
        //} else {
        //    player.setBackgrounded(true);
        //}
        //audioCapabilitiesReceiver.unregister();
        shutterView.setVisibility(View.VISIBLE);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("seekValue", playerPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthAspectRatio) {
        spinner.setVisibility(View.GONE);
        shutterView.setVisibility(View.GONE);
        surface.setVideoWidthHeightRatio(
                height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }

    @Override
    public void onText(String text) {

    }

    @Override
    public void onId3Metadata(Map<String, Object> metadata) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (player != null) {
            player.setSurface(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player != null) {
            player.blockingClearSurface();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
            //eventLogger.endSession();
            //eventLogger = null;
        }
    }

    private void toggleControlsVisibility()  {
        if (mediaController.isShowing()) {
            mediaController.hide();
            //debugRootView.setVisibility(View.GONE);
        } else {
            showControls();
        }
    }

    private void showControls() {
        mediaController.show(0);
        //debugRootView.setVisibility(View.VISIBLE);
    }
}
