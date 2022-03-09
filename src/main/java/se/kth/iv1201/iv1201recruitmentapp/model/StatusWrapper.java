package se.kth.iv1201.iv1201recruitmentapp.model;

/**
 * Class to represent a status, where value represents the value of a text
 * as defined by the database whilst text represents the graphical
 * representation of the value.
 */
public class StatusWrapper {
    private String value;
    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
