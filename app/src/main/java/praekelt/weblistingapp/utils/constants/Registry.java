package praekelt.weblistingapp.utils.constants;

import java.util.HashMap;

import praekelt.weblistingapp.models.extendModelBase.Character;
import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.models.extendModelBase.Post;
import praekelt.weblistingapp.models.extendModelBase.Review;
import praekelt.weblistingapp.models.extendModelBase.Reviewer;
import praekelt.weblistingapp.models.extendModelBase.Video;

public final class Registry {

    private Registry() {
        // restrict instantiation
    }

    private static final HashMap<String, HashMap> TYPE_CLASSES = new HashMap<String, HashMap>() {{
        put("ModelBase", new HashMap<String, Class>() {{
            put("detail", ModelBase.class);
        }});
        put("Post", new HashMap<String, Class>() {{
            put("detail", Post.class);
        }});
        put("Character", new HashMap<String, Class>() {{
            put("detail", Character.class);
        }});
        put("Review", new HashMap<String, Class>() {{
            put("detail", Review.class);
        }});
        put("Reviewer", new HashMap<String, Class>() {{
            put("detail", Reviewer.class);
        }});
        put("Video", new HashMap<String, Class>() {{
            put("detail", Video.class);
        }});
    }};

    public static Class getDetailClass(String key) {
        return (Class) TYPE_CLASSES.get(key).get("detail");
    }
}
