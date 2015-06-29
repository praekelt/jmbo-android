package praekelt.weblistingapp.utils;

import android.os.Bundle;

import praekelt.weblistingapp.models.ModelBase;


/**
 * Created by altus on 2015/03/24.
 */
public class SavedData {
    public Bundle listPosition;
    public String filter;
    public String inflatedData;

    public SavedData() {
    }

    public SavedData(Bundle listPosition, boolean inflatedState, String filter, String inflatedView, String prevInflatedView, String inflatedData) {
        this.listPosition = listPosition;
        this.filter = filter;
        this.inflatedData = inflatedData;
    }
}
