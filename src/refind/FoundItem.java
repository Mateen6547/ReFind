package refind;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */


public class FoundItem {
    // Attributes
    private String itemId;
    private String itemName;
    private String category;
    private String dateFound;
    private String locationFound;
    private String condition;
    private String storageLocation;
    private String description;
    
    
    // Constructor
    public FoundItem(String itemId, String itemName,
            String category, String dateFound,
            String locationFound, String condition,
            String storageLocation, String description) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.dateFound = dateFound;
        this.locationFound = locationFound;
        this.condition = condition;
        this.storageLocation = storageLocation;
        this.description = description;
    }
    
    
    // Getter methods
    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getDateFound() {
        return dateFound;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public String getCondition() {
        return condition;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getDescription() {
        return description;
    }
    
    
    // Setter methods
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    // Display item details
    public String displayDetails() {
        return "Item ID: " + itemId
                + "\nItem Name: " + itemName
                + "\nCategory: " + category
                + "\nDate Found: " + dateFound
                + "\nLocation Found: " + locationFound
                + "\nCondition: " + condition
                + "\nStorage Location: " + storageLocation
                + "\nDescription: " + description;
    }




    
   
}
