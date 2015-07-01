package praekelt.weblistingapp.utils.constants;

import java.util.HashMap;

import praekelt.weblistingapp.fragments.detailViews.ModelBaseDetailFragment;
import praekelt.weblistingapp.fragments.detailViews.PostDetailFragment;
import praekelt.weblistingapp.fragments.detailViews.VideoDetailFragment;
import praekelt.weblistingapp.models.ModelBase;
import praekelt.weblistingapp.models.extendModelBase.Post;
import praekelt.weblistingapp.models.extendModelBase.Video;

public final class Registry {

    private Registry() {
        // restrict instantiation
    }

    private static final HashMap<String, Class> TYPE_CLASSES = new HashMap<String, Class>() {{
        put("ModelBase", ModelBase.class);
        put("Post", Post.class);
        put("Video", Video.class);
    }};

    private static final HashMap<String, HashMap> DETAIL_CLASS = new HashMap<String, HashMap>() {{
        put("ModelBase", new HashMap<String, Class>() {{
            put("detail", ModelBaseDetailFragment.class);
        }});
        put("Post", new HashMap<String, Class>() {{
            put("detail", PostDetailFragment.class);
        }});
        put("Video", new HashMap<String, Class>() {{
            put("detail", VideoDetailFragment.class);
        }});
    }};

    public static Class getObjectClass(String key) {
        try {
            return TYPE_CLASSES.get(key);
        }catch (NullPointerException e){
            return ModelBase.class;
        }
    }

    public static Class getDetailClass(String key) {
        try {
            return (Class) DETAIL_CLASS.get(key).get("detail");
        }catch (NullPointerException e){
            return ModelBaseDetailFragment.class;
        }
    }
}
