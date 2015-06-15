package praekelt.weblistingapp.Rest.DetailModels;

import com.google.gson.annotations.Expose;

/**
 * Created by altus on 2015/06/03.
 */
public class Reviewer extends ModelBase {

    @Expose
    private String name;

    /**
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }
}
