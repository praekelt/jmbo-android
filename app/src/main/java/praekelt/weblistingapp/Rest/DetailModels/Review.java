package praekelt.weblistingapp.Rest.DetailModels;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by altus on 2015/06/03.
 */
public class Review extends ModelBase {

    @Expose
    private List<ModelBase> games;

    @Expose
    private String content;

    @Expose
    private Reviewer reviewer;

    @Expose
    private Integer rating;

    /**
     * @return
     * The games
     */
    public List<ModelBase> getGames() {
        return games;
    }

    /**
     * @param games
     * The games
     */
    public void setGames(List<ModelBase> games) {
        this.games = games;
    }

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

    /**
     * @return
     * The reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * @param reviewer
     * The reviewer
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * @return
     * The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating
     * The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
