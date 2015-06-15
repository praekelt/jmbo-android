package praekelt.weblistingapp.Rest.Models;

/**
 * Created by altus on 2015/05/26.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("anonymous_comments")
    @Expose
    private Boolean anonymousComments;
    @SerializedName("anonymous_likes")
    @Expose
    private Boolean anonymousLikes;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("comments_closed")
    @Expose
    private Boolean commentsClosed;
    @SerializedName("comments_enabled")
    @Expose
    private Boolean commentsEnabled;
    @Expose
    private String created;
    @SerializedName("crop_from")
    @Expose
    private String cropFrom;
    @SerializedName("date_taken")
    @Expose
    private String dateTaken;
    @Expose
    private String description;
    @Expose
    private Integer id;
    @Expose
    private String image;
    @SerializedName("image_attribution")
    @Expose
    private String imageAttribution;
    @SerializedName("image_detail_url")
    @Expose
    private String imageDetailUrl;
    @SerializedName("likes_closed")
    @Expose
    private Boolean likesClosed;
    @SerializedName("likes_enabled")
    @Expose
    private Boolean likesEnabled;
    @Expose
    private String modified;
    @SerializedName("owner_override")
    @Expose
    private String ownerOverride;
    @Expose
    private String permalink;
    @SerializedName("publish_on")
    @Expose
    private String publishOn;
    @SerializedName("resource_name")
    @Expose
    private String resourceName;
    @SerializedName("resource_uri")
    @Expose
    private String resourceUri;
    @SerializedName("retract_on")
    @Expose
    private Object retractOn;
    @Expose
    private String slug;
    @Expose
    private String state;
    @Expose
    private String subtitle;
    @Expose
    private String title;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("vote_total")
    @Expose
    private Integer voteTotal;

    /**
     *
     * @return
     * The anonymousComments
     */
    public Boolean getAnonymousComments() {
        return anonymousComments;
    }

    /**
     *
     * @param anonymousComments
     * The anonymous_comments
     */
    public void setAnonymousComments(Boolean anonymousComments) {
        this.anonymousComments = anonymousComments;
    }

    /**
     *
     * @return
     * The anonymousLikes
     */
    public Boolean getAnonymousLikes() {
        return anonymousLikes;
    }

    /**
     *
     * @param anonymousLikes
     * The anonymous_likes
     */
    public void setAnonymousLikes(Boolean anonymousLikes) {
        this.anonymousLikes = anonymousLikes;
    }

    /**
     *
     * @return
     * The className
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     * @param className
     * The class_name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     *
     * @return
     * The commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     *
     * @param commentCount
     * The comment_count
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     *
     * @return
     * The commentsClosed
     */
    public Boolean getCommentsClosed() {
        return commentsClosed;
    }

    /**
     *
     * @param commentsClosed
     * The comments_closed
     */
    public void setCommentsClosed(Boolean commentsClosed) {
        this.commentsClosed = commentsClosed;
    }

    /**
     *
     * @return
     * The commentsEnabled
     */
    public Boolean getCommentsEnabled() {
        return commentsEnabled;
    }

    /**
     *
     * @param commentsEnabled
     * The comments_enabled
     */
    public void setCommentsEnabled(Boolean commentsEnabled) {
        this.commentsEnabled = commentsEnabled;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The cropFrom
     */
    public String getCropFrom() {
        return cropFrom;
    }

    /**
     *
     * @param cropFrom
     * The crop_from
     */
    public void setCropFrom(String cropFrom) {
        this.cropFrom = cropFrom;
    }

    /**
     *
     * @return
     * The dateTaken
     */
    public String getDateTaken() {
        return dateTaken;
    }

    /**
     *
     * @param dateTaken
     * The date_taken
     */
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The imageAttribution
     */
    public String getImageAttribution() {
        return imageAttribution;
    }

    /**
     *
     * @param imageAttribution
     * The image_attribution
     */
    public void setImageAttribution(String imageAttribution) {
        this.imageAttribution = imageAttribution;
    }

    /**
     *
     * @return
     * The imageDetailUrl
     */
    public String getImageDetailUrl() {
        return imageDetailUrl;
    }

    /**
     *
     * @param imageDetailUrl
     * The image_detail_url
     */
    public void setImageDetailUrl(String imageDetailUrl) {
        this.imageDetailUrl = imageDetailUrl;
    }

    /**
     *
     * @return
     * The likesClosed
     */
    public Boolean getLikesClosed() {
        return likesClosed;
    }

    /**
     *
     * @param likesClosed
     * The likes_closed
     */
    public void setLikesClosed(Boolean likesClosed) {
        this.likesClosed = likesClosed;
    }

    /**
     *
     * @return
     * The likesEnabled
     */
    public Boolean getLikesEnabled() {
        return likesEnabled;
    }

    /**
     *
     * @param likesEnabled
     * The likes_enabled
     */
    public void setLikesEnabled(Boolean likesEnabled) {
        this.likesEnabled = likesEnabled;
    }

    /**
     *
     * @return
     * The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     *
     * @param modified
     * The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     *
     * @return
     * The ownerOverride
     */
    public String getOwnerOverride() {
        return ownerOverride;
    }

    /**
     *
     * @param ownerOverride
     * The owner_override
     */
    public void setOwnerOverride(String ownerOverride) {
        this.ownerOverride = ownerOverride;
    }

    /**
     *
     * @return
     * The permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     *
     * @param permalink
     * The permalink
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     *
     * @return
     * The publishOn
     */
    public String getPublishOn() {
        return publishOn;
    }

    /**
     *
     * @param publishOn
     * The publish_on
     */
    public void setPublishOn(String publishOn) {
        this.publishOn = publishOn;
    }

    /**
     *
     * @return
     * The resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     *
     * @param resourceName
     * The resource_name
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     *
     * @return
     * The resourceUri
     */
    public String getResourceUri() {
        return resourceUri;
    }

    /**
     *
     * @param resourceUri
     * The resource_uri
     */
    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    /**
     *
     * @return
     * The retractOn
     */
    public Object getRetractOn() {
        return retractOn;
    }

    /**
     *
     * @param retractOn
     * The retract_on
     */
    public void setRetractOn(Object retractOn) {
        this.retractOn = retractOn;
    }

    /**
     *
     * @return
     * The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     * The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     *
     * @param subtitle
     * The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The viewCount
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     *
     * @param viewCount
     * The view_count
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     *
     * @return
     * The voteTotal
     */
    public Integer getVoteTotal() {
        return voteTotal;
    }

    /**
     *
     * @param voteTotal
     * The vote_total
     */
    public void setVoteTotal(Integer voteTotal) {
        this.voteTotal = voteTotal;
    }

}
