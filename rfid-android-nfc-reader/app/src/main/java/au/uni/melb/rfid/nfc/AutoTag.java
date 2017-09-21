package au.uni.melb.rfid.nfc;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ao Li on 21/9/17.
 */
public class AutoTag {
    /**
     * AutoTag Id
     */
    @SerializedName("id")
    private String autoTagId;

    /**
     * AutoTag authorized
     */
    @SerializedName("authorized")
    private Boolean authorized;


    /**
     * AutoTag constructor
     */
    public AutoTag() {

    }

    /**
     * Sets the autoTag id
     *
     * @param id id to set
     */
    public void setId(String id) {
        autoTagId = id;
    }

    /**
     * Marks the autoTag id as authorized or unauthorized
     */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AutoTag && ((AutoTag) o).autoTagId == autoTagId;
    }
}
