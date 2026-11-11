package refind;

/*
 * ReFind - Lost and Found & Resource Recovery System
 * Section 1: Lost Item Management
 */

/**
 * @author [Your Name]
 *
 * LostItem
 * Represents a single lost-item report in the Lost and Found & Resource Recovery System.
 * Section 1: Lost Item Management
 */
public class LostItem {
    // Attributes
    private int itemId;
    private String itemName;
    private String category;
    private String colour;
    private String brand;
    private String description;
    private String lostLocation;
    private String dateLost;
    private String contactInfo;
    private String status;

    // Constructors
    public LostItem() {
        this.status = "Reported";
    }

    public LostItem(int itemId, String itemName, String category, String colour, String brand,
                    String description, String lostLocation, String dateLost, String contactInfo) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.colour = colour;
        this.brand = brand;
        this.description = description;
        this.lostLocation = lostLocation;
        this.dateLost = dateLost;
        this.contactInfo = contactInfo;
        this.status = "Reported";
    }

    // Getter methods
    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods
    public void setStatus(String status) {
        this.status = status;
    }

    // Display item details
    public String printDetails() {
        return "Item ID: " + itemId
                + "\nItem Name: " + itemName
                + "\nCategory: " + category
                + "\nColour: " + colour
                + "\nBrand: " + brand
                + "\nDescription: " + description
                + "\nLost Location: " + lostLocation
                + "\nDate Lost: " + dateLost
                + "\nContact Info: " + contactInfo
                + "\nStatus: " + status;
    }
}
