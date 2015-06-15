package praekelt.weblistingapp.Utils;

import android.os.Bundle;

import praekelt.weblistingapp.Rest.Models.Item;

/**
 * Created by altus on 2015/03/24.
 */
public class SavedData {
    public Bundle listPosition;
    public String filter;
    public Item inflatedData;

        public SavedData(Bundle listPosition, boolean inflatedState, String filter, String inflatedView, String prevInflatedView, Item inflatedData) {
            this.listPosition = listPosition;
            this.filter = filter;
            this.inflatedData = inflatedData;
        }
}
