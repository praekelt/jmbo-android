package praekelt.weblistingapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import junit.framework.TestCase;

import praekelt.weblistingapp.LoginArea.LoginActivity;

/**
 * Created by altus on 2015/06/08.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
    private Button playerButton;
    private TextView mediaDetailArtistName, mediaDetailSongTitle;
    private FrameLayout frameLayout;

    public MainActivityTest() {
        super(MainActivity.class);

    }

    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mainActivity = getActivity();

        frameLayout = (FrameLayout) mainActivity.findViewById(R.id.list_fragment);

        //PlayerArea
        playerButton = (Button) mainActivity.findViewById(R.id.playPause);
        mediaDetailArtistName = (TextView) mainActivity.findViewById(R.id.artistName);
        mediaDetailSongTitle = (TextView) mainActivity.findViewById(R.id.songTitle);

    }

    @MediumTest
    public void testPlayerButton_layout() {
        final View testView = mainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(testView, playerButton);

        assertTrue(View.VISIBLE == playerButton.getVisibility());

        final ViewGroup.LayoutParams layoutParams = playerButton.getLayoutParams();
        assertNotNull(layoutParams);

        assertEquals("Width not equal", layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @MediumTest
    public void testFrameLayout_layout() {
        final View mainView = mainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(mainView, frameLayout);

//        assertTrue(View.GONE == frameLayout.getVisibility());
    }

    @MediumTest
    public void testMediaAread_layout() {
        final View mediaView = mainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(mediaView, mediaDetailArtistName);
        ViewAsserts.assertOnScreen(mediaView, mediaDetailSongTitle);

        assertTrue(View.VISIBLE == mediaDetailArtistName.getVisibility());
        assertTrue(View.VISIBLE == mediaDetailSongTitle.getVisibility());
    }

//    @MediumTest
//    public void testMediaPlayerButton_clickButtonAndExpectInfoText() {
//        TouchUtils.clickView(this, playerButton);
//    }

    @MediumTest
    public void testLoginActivity_launch() {

        // TODO
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
                LoginActivity.class.getName(), null, false);

        // find button and click it
        final View profileButton =  mainActivity.findViewById(R.id.profile);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profileButton.requestFocus();
                profileButton.performClick();
            }
        });

        // TODO Launches LoginActivity because MainActivity checks for profile data before launching intent
        // Wait 2 seconds for the start of the activity
        LoginActivity loginActivity = (LoginActivity) monitor.waitForActivityWithTimeout(2000);

        assertNotNull(loginActivity);

        // Search for the textView
        TextView textView = (TextView) loginActivity
                .findViewById(R.id.email);

        // check that the TextView is on the screen
        ViewAsserts.assertOnScreen(loginActivity.getWindow().getDecorView(),
                textView);

        // Press back to return to original activity
        // We have to manage the initial position within the emulator
        this.sendKeys(KeyEvent.KEYCODE_BACK);

    }

    //TODO should be in UpdateProfileActivityTest
//    @MediumTest
//    public void testUpdateProfileActivity_launchErroniousData() {
//        Instrumentation mInstrumentation = getInstrumentation();
//        // We register our interest in the activity
//        Instrumentation.ActivityMonitor monitor = mInstrumentation.addMonitor(UpdateProfileActivity.class.getName(), null, false);
//        // We launch it
//        Intent intent = new Intent(mainActivity, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClassName(mInstrumentation.getTargetContext(), UpdateProfileActivity.class.getName());
//        intent.putExtra("profileData", profile);
//        mInstrumentation.startActivitySync(intent);
//
//        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);
//        assertNotNull(currentActivity);
//        // We register our interest in the next activity from the sequence in this use case
//        mInstrumentation.removeMonitor(monitor);
//        monitor = mInstrumentation.addMonitor(UpdateProfileActivity.class.getName(), null, false);
//    }



    public void testOnCreate() throws Exception {

    }

    public void testOnStop() throws Exception {

    }

    public void testOnPause() throws Exception {

    }

    public void testOnSaveInstanceState() throws Exception {

    }

    public void testOnBackPressed() throws Exception {

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}