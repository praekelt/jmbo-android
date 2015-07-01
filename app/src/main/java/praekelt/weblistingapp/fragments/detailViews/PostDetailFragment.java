package praekelt.weblistingapp.fragments.detailViews;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.R;

public class PostDetailFragment extends ModelBaseDetailFragment {
    private TextView content;

    public PostDetailFragment() {
        // Required empty public constructor
        super();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        content = (TextView) view.findViewById(R.id.content_text);
    }

    public void onStart() {
        super.onStart();
    }

    public void setData(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        super.setData(obj);

        Method m = obj.getClass().getMethod("getContent");
        content.setText(Html.fromHtml((String) (m.invoke(obj))));
    }
}
