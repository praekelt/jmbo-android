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

import praekelt.weblistingapp.loginArea.LoginActivity;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private MainActivity mainActivity;
    private FrameLayout frameLayout;

    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
    }

    @Before
    public void setup() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();

        frameLayout = (FrameLayout) mainActivity.findViewById(R.id.list_fragment);
    }

    @Test
    public void testFrameLayout_layout() {
        assertThat(frameLayout, notNullValue());
        assertThat(frameLayout.getVisibility(), equalTo(View.VISIBLE));
    }


//    @Test
//    public void testProfileButton() {
//    // TODO profile button can launch one of two Activities depending on profileData in MainActivity
//        // find button and click it
//        final View profileButton =  mainActivity.findViewById(R.id.profile);
//        mainActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                profileButton.requestFocus();
//                profileButton.performClick();
//            }
//        });
//        Intent startedIntent = shadowOf(mainActivity).getNextStartedActivity();
//        // Verify the intent was started with correct result extra
//        assertThat(startedIntent.getComponent().getClassName(), equalTo(LoginActivity.class.getName()));
//    }

    @After
    public void tearDown() throws Exception {
    }
}