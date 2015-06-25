package praekelt.weblistingapp.Rest.DetailModels;

import com.google.gson.annotations.Expose;

public class Post extends ModelBase {

    @Expose
    private String content;

    /**
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
