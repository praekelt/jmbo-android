package praekelt.weblistingapp.restfullApi;

import com.google.gson.JsonElement;

import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.restfullApi.restfullModels.VerticalThumbnailListing;
import retrofit.Callback;
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
        void getItem(@Path("slug") Callback<ModelBase> callback);

        @GET("/post/post/?format=json")
        //public JsonElement getTestPost(String item);
        void getTestPost(Callback<JsonElement> response);

        @GET("/api/v1/listing/1/?format=json")
        void getVideoListing(Callback<VerticalThumbnailListing> response);

        @GET("/{uri}?format=json")
        void getVideoStream(@Path(value="uri", encode=false) String uri, Callback<JsonElement> response);
    }
}
