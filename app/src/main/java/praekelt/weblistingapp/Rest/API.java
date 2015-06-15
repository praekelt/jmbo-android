package praekelt.weblistingapp.Rest;

import com.google.gson.JsonElement;

import praekelt.weblistingapp.Rest.Models.Item;
import praekelt.weblistingapp.Rest.Models.VerticalThumbnailListing;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by altus on 2015/04/15.
 */
public class API {

    public interface JMBOApi {

        //@Headers("Content-Type: application/json")
        @GET("/listing/game-index/?format=json")
        void getVerticalThumbnailListing(Callback<VerticalThumbnailListing> callback);

        @GET("/detail/{slug}/?format=json")
        void getItem(@Path("slug") Callback<Item> callback);

        @GET("/post/post/?format=json")
        //public JsonElement getTestPost(String item);
        void getTestPost(Callback<JsonElement> response);
    }
}
