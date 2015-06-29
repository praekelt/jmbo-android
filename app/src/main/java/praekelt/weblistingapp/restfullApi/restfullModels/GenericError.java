package praekelt.weblistingapp.restfullApi.restfullModels;

import com.google.gson.annotations.Expose;

/**
 * Created by altus on 2015/06/29.
 */
public class GenericError {

    @Expose
    private String error;

    /**
     * @return
     * The error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     * The error
     */
    public void setError(String error) {
        this.error = error;
    }
}
