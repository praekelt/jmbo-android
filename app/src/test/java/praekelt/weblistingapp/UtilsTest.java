package praekelt.weblistingapp;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.restfullApi.API;
import praekelt.weblistingapp.restfullApi.restfullModels.GenericError;
import praekelt.weblistingapp.restfullApi.restfullModels.VerticalThumbnailListing;
import praekelt.weblistingapp.utils.JSONUtils;
import praekelt.weblistingapp.utils.constants.Constants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UtilsTest {

    @Test
    public void utilsTest() {
        JsonElement element = new JsonParser().parse(TestConstants.postJSON);
        Object obj = JSONUtils.getDetailObject(element);

        try {
            Method m = obj.getClass().getMethod("getClass");
            m.invoke(obj);

            m = obj.getClass().getMethod("getTitle");
            m.invoke(obj);

            m = obj.getClass().getMethod("getImageDetailUrl");
            m.invoke(obj);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRestApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                        //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);
        api.getListing(new Callback<VerticalThumbnailListing>() {

            @Override
            public void success(VerticalThumbnailListing listing, Response response) {
                Log.d("API Returned Title: ", listing.getTitle());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("API Result: ", "Failure");
            }
        });
    }


}
