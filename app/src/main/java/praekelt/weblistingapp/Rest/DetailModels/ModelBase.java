package praekelt.weblistingapp.Rest.DetailModels;

import com.google.gson.annotations.Expose;

public class ModelBase {

    @Expose
    private Boolean anonymous_comments;

    @Expose
    private Boolean anonymous_likes;

    @Expose
    private String class_name;

    @Expose
    private Integer comment_count;

    @Expose
    private Boolean comments_closed;

    @Expose
    private Boolean comments_enabled;

    @Expose
    private String created;

    @Expose
    private String crop_from;

    @Expose
    private String date_taken;

    @Expose
    private String description;

    @Expose
    private Integer id;

    @Expose
    private String image;

    @Expose
    private String image_attribution;

    @Expose
    private String image_detail_url;

    @Expose
    private Boolean likes_closed;

    @Expose
    private Boolean likes_enabled;

    @Expose
    private String modified;

    @Expose
    private String owner_override;

    @Expose
    private String permalink;

    @Expose
    private String publish_on;

    @Expose
    private String resource_name;

    @Expose
    private String resource_uri;

    @Expose
    private Object retract_on;

    @Expose
    private String slug;

    @Expose
    private String state;

    @Expose
    private String subtitle;

    @Expose
    private String title;

    @Expose
    private Integer view_count;

    @Expose
    private Integer vote_total;



    /**
     * @return
     * The anonymous_comments
     */
    public Boolean getAnonymousComments() {
        return anonymous_comments;
    }

    /**
     * @param anonymous_comments
     * The anonymous_comments
     */
    public void setAnonymousComments(Boolean anonymous_comments) {
        this.anonymous_comments = anonymous_comments;
    }

    /**
     * @return
     * The anonymous_likes
     */
    public Boolean getAnonymousLikes() {
        return anonymous_likes;
    }

    /**
     * @param anonymous_likes
     * The anonymous_likes
     */
    public void setAnonymousLikes(Boolean anonymous_likes) {
        this.anonymous_likes = anonymous_likes;
    }

    /**
     * @return
     * The class_name
     */
    public String getClassName() {
        return class_name;
    }

    /**
     * @param class_name
     * The class_name
     */
    public void setClassName(String class_name) {
        this.class_name = class_name;
    }

    /**
     * @return
     * The comment_count
     */
    public Integer getCommentCount() {
        return comment_count;
    }

    /**
     * @param comment_count
     * The comment_count
     */
    public void setCommentCount(Integer comment_count) {
        this.comment_count = comment_count;
    }

    /**
     * @return
     * The comments_closed
     */
    public Boolean getCommentsClosed() {
        return comments_closed;
    }

    /**
     * @param comments_closed
     * The comments_closed
     */
    public void setCommentsClosed(Boolean comments_closed) {
        this.comments_closed = comments_closed;
    }

    /**
     * @return
     * The comments_enabled
     */
    public Boolean getCommentsEnabled() {
        return comments_enabled;
    }

    /**
     * @param comments_enabled
     * The comments_enabled
     */
    public void setCommentsEnabled(Boolean comments_enabled) {
        this.comments_enabled = comments_enabled;
    }

    /**
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return
     * The crop_from
     */
    public String getCropFrom() {
        return crop_from;
    }

    /**
     * @param crop_from
     * The crop_from
     */
    public void setCropFrom(String crop_from) {
        this.crop_from = crop_from;
    }

    /**
     * @return
     * The date_taken
     */
    public String getDateTaken() {
        return date_taken;
    }

    /**
     * @param date_taken
     * The date_taken
     */
    public void setDateTaken(String date_taken) {
        this.date_taken = date_taken;
    }

    /**
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return
     * The image_attribution
     */
    public String getImageAttribution() {
        return image_attribution;
    }

    /**
     * @param image_attribution
     * The image_attribution
     */
    public void setImageAttribution(String image_attribution) {
        this.image_attribution = image_attribution;
    }

    /**
     * @return
     * The image_detail_url
     */
    public String getImageDetailUrl() {
        return image_detail_url;
    }

    /**
     * @param image_detail_url
     * The image_detail_url
     */
    public void setImageDetailUrl(String image_detail_url) {
        this.image_detail_url = image_detail_url;
    }

    /**
     * @return
     * The likes_closed
     */
    public Boolean getLikesClosed() {
        return likes_closed;
    }

    /**
     * @param likes_closed
     * The likes_closed
     */
    public void setLikesClosed(Boolean likes_closed) {
        this.likes_closed = likes_closed;
    }

    /**
     * @return
     * The likes_enabled
     */
    public Boolean getLikesEnabled() {
        return likes_enabled;
    }

    /**
     * @param likes_enabled
     * The likes_enabled
     */
    public void setLikesEnabled(Boolean likes_enabled) {
        this.likes_enabled = likes_enabled;
    }

    /**
     * @return
     * The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     * @param modified
     * The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * @return
     * The owner_override
     */
    public String getOwnerOverride() {
        return owner_override;
    }

    /**
     * @param owner_override
     * The owner_override
     */
    public void setOwnerOverride(String owner_override) {
        this.owner_override = owner_override;
    }

    /**
     * @return
     * The permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * @param permalink
     * The permalink
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     * @return
     * The publish_on
     */
    public String getPublishOn() {
        return publish_on;
    }

    /**
     * @param publish_on
     * The publish_on
     */
    public void setPublishOn(String publish_on) {
        this.publish_on = publish_on;
    }

    /**
     * @return
     * The resource_name
     */
    public String getResourceName() {
        return resource_name;
    }

    /**
     * @param resource_name
     * The resource_name
     */
    public void setResourceName(String resource_name) {
        this.resource_name = resource_name;
    }

    /**
     * @return
     * The resource_uri
     */
    public String getResourceUri() {
        return resource_uri;
    }

    /**
     * @param resource_uri
     * The resource_uri
     */
    public void setResourceUri(String resource_uri) {
        this.resource_uri = resource_uri;
    }

    /**
     * @return
     * The retract_on
     */
    public Object getRetractOn() {
        return retract_on;
    }

    /**
     * @param retract_on
     * The retract_on
     */
    public void setRetractOn(Object retract_on) {
        this.retract_on = retract_on;
    }

    /**
     * @return
     * The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug
     * The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return
     * The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle
     * The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     * The view_count
     */
    public Integer getViewCount() {
        return view_count;
    }

    /**
     * @param view_count
     * The view_count
     */
    public void setViewCount(Integer view_count) {
        this.view_count = view_count;
    }

    /**
     * @return
     * The vote_total
     */
    public Integer getVoteTotal() {
        return vote_total;
    }

    /**
     * @param vote_total
     * The vote_total
     */
    public void setVoteTotal(Integer vote_total) {
        this.vote_total = vote_total;
    }

}

