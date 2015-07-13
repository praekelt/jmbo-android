package praekelt.weblistingapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.utils.constants.Registry;

/**
 * Created by altus on 2015/06/11.
 */
public class JSONUtils {

    public static Object getDetailObject(JsonElement element){

        Gson gson = new GsonBuilder().create();

        return gson.fromJson(element, Registry.getObjectClass(getClassType(element)));
    }

    private static String getClassType(JsonElement element) {
        Gson gson = new GsonBuilder().create();

        ModelBase obj = gson.fromJson(element, ModelBase.class);
        Log.d("Model Object Class: ", obj.getClassName());
        return obj.getClassName();
    }


}
