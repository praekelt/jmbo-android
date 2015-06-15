package praekelt.weblistingapp.Rest.Models;

/**
 * Created by altus on 2015/05/26.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerticalThumbnailListing {

    @Expose
    private Integer count;
    @SerializedName("display_title_tiled")
    @Expose
    private Boolean displayTitleTiled;
    @SerializedName("enable_syndication")
    @Expose
    private Boolean enableSyndication;
    @Expose
    private Integer id;
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("items_per_page")
    @Expose
    private Integer itemsPerPage;
    @Expose
    private String permalink;
    @SerializedName("resource_name")
    @Expose
    private String resourceName;
    @SerializedName("resource_uri")
    @Expose
    private String resourceUri;
    @Expose
    private String slug;
    @Expose
    private String style;
    @Expose
    private String subtitle;
    @Expose
    private String title;
    @SerializedName("view_modifier")
    @Expose
    private String viewModifier;

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The displayTitleTiled
     */
    public Boolean getDisplayTitleTiled() {
        return displayTitleTiled;
    }

    /**
     *
     * @param displayTitleTiled
     * The display_title_tiled
     */
    public void setDisplayTitleTiled(Boolean displayTitleTiled) {
        this.displayTitleTiled = displayTitleTiled;
    }

    /**
     *
     * @return
     * The enableSyndication
     */
    public Boolean getEnableSyndication() {
        return enableSyndication;
    }

    /**
     *
     * @param enableSyndication
     * The enable_syndication
     */
    public void setEnableSyndication(Boolean enableSyndication) {
        this.enableSyndication = enableSyndication;
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
     * The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     *
     * @return
     * The itemsPerPage
     */
    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     *
     * @param itemsPerPage
     * The items_per_page
     */
    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
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
     * The style
     */
    public String getStyle() {
        return style;
    }

    /**
     *
     * @param style
     * The style
     */
    public void setStyle(String style) {
        this.style = style;
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
     * The viewModifier
     */
    public String getViewModifier() {
        return viewModifier;
    }

    /**
     *
     * @param viewModifier
     * The view_modifier
     */
    public void setViewModifier(String viewModifier) {
        this.viewModifier = viewModifier;
    }

}


