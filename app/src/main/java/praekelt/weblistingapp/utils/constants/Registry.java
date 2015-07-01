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
        // prevents instantiation
    }

    private static final HashMap<String, Class> TYPE_CLASSES = new HashMap<String, Class>() {{
        put("ModelBase", ModelBase.class);
        put("Post", Post.class);
        put("Video", Video.class);
    }};

    private static final HashMap<String, Class> DETAIL_CLASS = new HashMap<String, Class>() {{
        put("ModelBase",  ModelBaseDetailFragment.class);
        put("Post",  PostDetailFragment.class);
        put("Video", VideoDetailFragment.class);
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
            return DETAIL_CLASS.get(key);
        }catch (NullPointerException e){
            return ModelBaseDetailFragment.class;
        }
    }
}
