package praekelt.weblistingapp.models.extendModelBase;

import com.google.gson.annotations.Expose;

import praekelt.weblistingapp.models.ModelBase;

/**
 * Created by altus on 2015/06/25.
 */
public class Video extends ModelBase{

    @Expose
    private String stream;

    /**
     * @return
     * The stream
     */
    public String getStream() {
        return stream;
    }

    /**
     * @param stream
     * The stream path
     */
    public void setStream(String stream) {
        this.stream = stream;
    }
}
