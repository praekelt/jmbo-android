package praekelt.weblistingapp.Rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by altus on 2015/04/15.
 */

public class ReceivedProfileData implements Serializable {

    @Expose
    private int id;

    @Expose
    private String url;

    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @Expose
    private String email;

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;

    @SerializedName("receive_email")
    @Expose
    private boolean receiveEmail;

    @SerializedName("receive_sms")
    @Expose
    private boolean receiveSms;


    @Expose
    private String image;


    @Expose
    private String detail;

    @Expose
    private String authentication;


/**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     *
     * @param mobileNumber
     * The mobile_number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     *
     * @return
     * The receiveEmail
     */
    public boolean isReceiveEmail() {
        return receiveEmail;
    }

    /**
     *
     * @param receiveEmail
     * The receive_email
     */
    public void setReceiveEmail(boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    /**
     *
     * @return
     * The receiveSms
     */
    public boolean isReceiveSms() {
        return receiveSms;
    }

    /**
     *
     * @param receiveSms
     * The receive_sms
     */
    public void setReceiveSms(boolean receiveSms) {
        this.receiveSms = receiveSms;
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
     * The detail
     */
    public String getError() {
        return detail;
    }

    /**
     *
     * @param detail
     * The detail
     */
    public void setError(String detail) {
        this.detail = detail;
    }

    /**
     *
     * @return
     * The authentication
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     *
     * @param authentication
     * The detail
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

}
