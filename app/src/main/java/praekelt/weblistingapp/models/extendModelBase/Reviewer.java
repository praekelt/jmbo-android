package praekelt.weblistingapp.models.extendModelBase;

import com.google.gson.annotations.Expose;

import praekelt.weblistingapp.models.ModelBase;

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
