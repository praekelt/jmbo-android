package praekelt.weblistingapp.fragments.detailViews;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.util.Util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import praekelt.weblistingapp.MainActivity;
import praekelt.weblistingapp.R;
import praekelt.weblistingapp.utils.mediaPlayer.HlsRendererBuilder;
import praekelt.weblistingapp.utils.mediaPlayer.Player;

public class VideoDetailFragment extends ModelBaseDetailFragment implements Player.Listener, Player.TextListener, Player.Id3MetadataListener, SurfaceHolder.Callback, Player.InternalErrorListener {
    private com.google.android.exoplayer.VideoSurfaceView surface;
    private View shutterView;
    private ProgressBar spinner;
    private View root;
    private TextView content;
    private TextView title;

    private Player player;
    private MediaController mediaController;
    private boolean playerNeedsPrepare = true;
    private  HlsRendererBuilder builder;
    private long playerPosition = 0;
    private boolean landscape;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong("seekValue");
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

        surface = (com.google.android.exoplayer.VideoSurfaceView) view.findViewById(R.id.surface_view);
        surface.getHolder().addCallback(this);

        //Shutter to create an overlay to hide video view video when onPause is called.
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

        //TODO probably shouldnt listit here
        AudioCapabilities audioCapabilities = new AudioCapabilities(new int[]{AudioFormat.ENCODING_DEFAULT, AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT}, 2);

        builder = new HlsRendererBuilder(getActivity(), userAgent, ((String) m.invoke(obj)), null, audioCapabilities);

        preparePlayer();

        if(!landscape) {
            m = obj.getClass().getMethod("getTitle");
            title.setText((String) m.invoke(obj));

            m = obj.getClass().getMethod("getContent");
            content.setText(Html.fromHtml((String) (m.invoke(obj))));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        releasePlayer();

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
            player.setInternalErrorListener(this);
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
            //updateButtonVisibilities();
        }
        player.setSurface(surface.getHolder().getSurface());
        player.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    private void toggleControlsVisibility()  {
        if (mediaController.isShowing()) {
            mediaController.hide();
        } else {
            showControls();
        }
    }

    private void showControls() {
        mediaController.show(0);
    }

    @Override
    public void onRendererInitializationError(Exception e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onAudioTrackInitializationError(AudioTrack.InitializationException e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onAudioTrackWriteError(AudioTrack.WriteException e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onCryptoError(MediaCodec.CryptoException e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onLoadError(int sourceId, IOException e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }

    @Override
    public void onDrmSessionManagerError(Exception e) {
        ((MainActivity) getActivity()).toast(getString(R.string.video_error));
        getActivity().onBackPressed();
    }
}
