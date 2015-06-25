package praekelt.weblistingapp;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

import praekelt.weblistingapp.Rest.DetailModels.ModelBase;

/**
 * Created by altus on 2015/03/03.
 *
 * Fragment that houses teh media player for audio streaming purposes
 * Retains instance across state changes to ensure the media player dos not lose its context
 */

public class PlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener{
    private MediaPlayer mediaPlayer;

    Button playPause;

    private TextView songTitle;
    private TextView artistName;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        playPause = (Button) v.findViewById(R.id.playPause);
        playPause.getBackground().setColorFilter(0xffd45cc0, PorterDuff.Mode.MULTIPLY);
        songTitle = (TextView) v.findViewById(R.id.songTitle);
        artistName = (TextView) v.findViewById(R.id.artistName);

        artistName.setSelected(true);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean on = ((ToggleButton) view).isChecked();
                if (on) {
                    play();
                    Log.i("Media Player State: ", "Playing");
                } else {
                   stop();
                    Log.i("Media Player State: ", "Stopped");
                }
            }
        });

        return v;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i("Player Ready: ", "True");
        mediaPlayer.start();
        mediaPlayer.setVolume((10), (10));
        mediaPlayer.setWakeMode(getActivity().getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        WifiManager.WifiLock wifiLock = ((WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

        wifiLock.acquire();
    }


    private void play() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(String.valueOf(Uri.parse("http://41.21.178.245:1935/jac-pri/jac-pri.stream/playlist.m3u8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
    }

    private void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();    // set state to idle
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void setDetail(ModelBase mb) {
        //songTitle.setText(mb.title);
        //artistName.setText(mb.subtitle);
    }
}
