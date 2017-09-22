package au.uni.melb.rfid.notifier.model;


import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    public String mId;
    @SerializedName("text")
    public String mText;
    @SerializedName("complete")
    private boolean mComplete;

    public Notification() {

    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Initializes a new Notification
     *
     * @param mText The item text
     * @param mId   The item id
     */
    public Notification(String mText, String mId) {
        this.setText(mText);
        this.setId(mId);
        this.setComplete(false);
    }

    /**
     * Returns the item text
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets the item text
     *
     * @param text text to set
     */
    public final void setText(String text) {
        mText = text;
    }

    /**
     * Returns the item id
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the item id
     *
     * @param id id to set
     */
    public final void setId(String id) {
        mId = id;
    }

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return mComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Notification && ((Notification) o).mId == mId;
    }
}
