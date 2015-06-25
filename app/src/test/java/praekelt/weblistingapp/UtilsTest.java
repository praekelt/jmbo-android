package praekelt.weblistingapp;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import praekelt.weblistingapp.Utils.JSONUtils;

/**
 * Created by altus on 2015/06/11.
 */
public class UtilsTest {

    @Test
    public void utilsTest() {
        JsonElement element = new JsonParser().parse(TestConstants.postJSON);
        try {
            JSONUtils.getDetailObject(element);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
