package comp90018.autotag;


public class ToDoItem {

    @com.google.gson.annotations.SerializedName("id")
    public String ncfId;
    @com.google.gson.annotations.SerializedName("text")
    public String ncfText;
    @com.google.gson.annotations.SerializedName("complete")
    private boolean ncfComplete;

    public ToDoItem() {

    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Initializes a new ToDoItem
     *
     * @param ncfText
     *            The item text
     * @param ncfId
     *            The item id
     */
    public ToDoItem(String ncfText, String ncfId) {
        this.setText(ncfText);
        this.setId(ncfId);
        this.setComplete(false);
    }

    /**
     * Returns the item text
     */
    public String getText() {
        return ncfText;
    }

    /**
     * Sets the item text
     *
     * @param text
     *            text to set
     */
    public final void setText(String text) {
        ncfText = text;
    }

    /**
     * Returns the item id
     */
    public String getId() {
        return ncfId;
    }

    /**
     * Sets the item id
     *
     * @param id
     *            id to set
     */
    public final void setId(String id) {
        ncfId = id;
    }

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return ncfComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        ncfComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).ncfId == ncfId;
    }
}
