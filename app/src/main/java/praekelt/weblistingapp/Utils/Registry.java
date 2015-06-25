package praekelt.weblistingapp.Utils;

import java.util.HashMap;

import praekelt.weblistingapp.Rest.DetailModels.Character;
import praekelt.weblistingapp.Rest.DetailModels.ModelBase;
import praekelt.weblistingapp.Rest.DetailModels.Post;
import praekelt.weblistingapp.Rest.DetailModels.Review;
import praekelt.weblistingapp.Rest.DetailModels.Reviewer;

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
    }};

    public static Class getDetailClass(String key) {
        return (Class) TYPE_CLASSES.get(key).get("detail");
    }
}
