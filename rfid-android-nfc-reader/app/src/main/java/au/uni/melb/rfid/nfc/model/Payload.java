package au.uni.melb.rfid.nfc.model;

import com.microsoft.windowsazure.mobileservices.table.DateTimeOffset;

/**
 * Created by dagrawal on 29-Sep-17.
 */

public class Payload {
    @com.google.gson.annotations.SerializedName("Id")
    private String id;
    @com.google.gson.annotations.SerializedName("Authorized")
    private boolean authorized;
    @com.google.gson.annotations.SerializedName("CreatedAt")
    private DateTimeOffset createdAt;
    @com.google.gson.annotations.SerializedName("UpdatedAt")
    private DateTimeOffset updatedAt;
    @com.google.gson.annotations.SerializedName("Version")
    private String version;
    @com.google.gson.annotations.SerializedName("Deleted")
    private boolean deleted;

    public Payload(String id, boolean authorized) {
        this.id = id;
        this.authorized = authorized;
    }

    public Payload() {

    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public DateTimeOffset getCreatedAt() {
        return createdAt;
    }

    protected void setCreatedAt(DateTimeOffset createdAt) {
        this.createdAt = createdAt;
    }

    public DateTimeOffset getUpdatedAt() {
        return updatedAt;
    }

    protected void setUpdatedAt(DateTimeOffset updatedAt) {
        this.updatedAt = updatedAt;
    }


    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payload payload = (Payload) o;

        return id != null ? id.equals(payload.id) : payload.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
