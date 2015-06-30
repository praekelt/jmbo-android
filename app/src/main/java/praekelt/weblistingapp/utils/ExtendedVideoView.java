package praekelt.weblistingapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by altus on 2015/06/30.
 */
public class ExtendedVideoView extends VideoView {
    public ExtendedVideoView(Context context) {
        super(context);
    }


    public ExtendedVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == View.VISIBLE) {
            super.onWindowVisibilityChanged(visibility);
        }
    }
}
