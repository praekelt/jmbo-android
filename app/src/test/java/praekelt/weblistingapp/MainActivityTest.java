package praekelt.weblistingapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import praekelt.weblistingapp.LoginArea.LoginActivity;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by altus on 2015/06/08.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private Button playerButton;
    private TextView mediaDetailArtistName, mediaDetailSongTitle;
    private FrameLayout frameLayout;

    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
    }

    @Before
    public void setup() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

        frameLayout = (FrameLayout) mainActivity.findViewById(R.id.list_fragment);

        playerButton = (Button) mainActivity.findViewById(R.id.playPause);
        mediaDetailArtistName = (TextView) mainActivity.findViewById(R.id.artistName);
        mediaDetailSongTitle = (TextView) mainActivity.findViewById(R.id.songTitle);
    }

    @Test
    public void testPlayerButton_layout() throws Exception{
        assertThat(playerButton, notNullValue());

        assertThat(playerButton.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testFrameLayout_layout() {
        assertThat(frameLayout, notNullValue());
        assertThat(frameLayout.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testMediaArea_layout() {

        assertThat(mediaDetailArtistName, notNullValue());
        assertThat(mediaDetailArtistName.getVisibility(), equalTo(View.VISIBLE));

        assertThat(mediaDetailSongTitle, notNullValue());
        assertThat(mediaDetailSongTitle.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testProfileButton() {
    // TODO profile button can launch one of two Activities depending on profileData in MainActivity
        // find button and click it
        final View profileButton =  mainActivity.findViewById(R.id.profile);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profileButton.requestFocus();
                profileButton.performClick();
            }
        });
        Intent startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        // Verify the intent was started with correct result extra
        assertThat(startedIntent.getComponent().getClassName(), equalTo(LoginActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
    }
}