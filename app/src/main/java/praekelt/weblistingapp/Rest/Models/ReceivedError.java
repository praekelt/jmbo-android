package praekelt.weblistingapp.Rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altus on 2015/04/16.
 */
public class ReceivedError {

    @Expose
    private List<String> username = new ArrayList<String>();
    @SerializedName("first_name")
    @Expose
    private List<String> firstName = new ArrayList<String>();
    @SerializedName("last_name")
    @Expose
    private List<String> lastName = new ArrayList<String>();
    @Expose
    private List<String> email = new ArrayList<String>();
    @SerializedName("mobile_number")
    @Expose
    private List<String> mobileNumber = new ArrayList<String>();

    /**
     *
     * @return
     * The username
     */
    public List<String> getUsername() {
        if(username.size() > 0) {
            return username;
        }else {
            return null;
        }

    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(List<String> username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The firstName
     */
    public List<String> getFirstName() {
        if(firstName.size() > 0) {
            return firstName;
        }else {
            return null;
        }
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(List<String> firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public List<String> getLastName() {
        if(lastName.size() > 0) {
            return lastName;
        }else {
            return null;
        }
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public List<String> getEmail() {
        if(email.size() > 0) {
            return email;
        }else {
            return null;
        }
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(List<String> email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The mobileNumber
     */
    public List<String> getMobileNumber() {
        if(mobileNumber.size() > 0) {
            return mobileNumber;
        }else {
            return null;
        }
    }

    /**
     *
     * @param mobileNumber
     * The mobile_number
     */
    public void setMobileNumber(List<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}