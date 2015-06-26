package praekelt.weblistingapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.utils.constants.Registry;

/**
 * Created by altus on 2015/06/11.
 */
public class JSONUtils {

    public static Object getDetailObject(JsonElement element) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Gson gson = new GsonBuilder().create();

        Object obj = gson.fromJson(element, Registry.getDetailClass(getClassType(element)));

        // TODO This way does not parse parent classes
        //Method m = obj.getClass().getDeclaredMethod("getClassName");
        //Log.d("Object Class: ", (String) m.invoke(obj));

        return obj;
    }

    private static String getClassType(JsonElement element) {
        Gson gson = new GsonBuilder().create();

        ModelBase obj = gson.fromJson(element, ModelBase.class);
        Log.d("Model Object Class: ", obj.getClassName());
        return obj.getClassName();
    }


}
