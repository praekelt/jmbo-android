package praekelt.weblistingapp.restfullApi;

import com.google.gson.JsonElement;

import praekelt.weblistingapp.restfullApi.restfullModels.VerticalThumbnailListing;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public class API {

    public interface JMBOApi {
        @GET("/api/v1/listing/1/?format=json")
        void getListing(Callback<VerticalThumbnailListing> response);

        @GET("/{uri}?format=json")
        void getDetail(@Path(value="uri", encode=false) String uri, Callback<JsonElement> response);
    }
}
