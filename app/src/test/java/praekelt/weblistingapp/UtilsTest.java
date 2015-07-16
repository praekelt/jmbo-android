package praekelt.weblistingapp;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.utils.JSONUtils;

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
}
