package praekelt.weblistingapp.Rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altus on 2015/04/16.
 */
public class SentProfileData {

    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @Expose
    private String username;

    @SerializedName("receive_email")
    @Expose
    private boolean receiveEmail;

    @SerializedName("receive_sms")
    @Expose
    private boolean receiveSms;

    @Expose
    private String password;

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
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
