package praekelt.weblistingapp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by altus on 2015/04/01.
 */
public class JSONValues {
    public static String contentType(JSONObject input) throws JSONException {
        String output = (input.getJSONObject("card").getString("content_type"));
        return output;
    }
}
