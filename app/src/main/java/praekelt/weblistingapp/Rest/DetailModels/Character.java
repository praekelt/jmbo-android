package praekelt.weblistingapp.Rest.DetailModels;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Character extends ModelBase {

    @Expose
    private List<ModelBase> games;

    /**
     * @return
     * The items
     */
    public List<ModelBase> getGames() {
        return games;
    }

    /**
     * @param games
     * The items
     */
    public void setGames(List<ModelBase> games) {
        this.games = games;
    }
}
