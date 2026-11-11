package refind;

import java.util.ArrayList;

/**
 * @author [Your Name]
 *
 * LostItemManager
 * Stores and manages all lost-item reports.
 * Section 1: Lost Item Management
 *
 * NOTE (Sprint 1): The UI is complete and navigable. Method bodies are basic
 * placeholders/stubs and will be fully implemented in a later sprint.
 */
public class LostItemManager {

    // Attributes
    private ArrayList<LostItem> lostItems;

    // Constructor
    public LostItemManager() {
        lostItems = new ArrayList<>();
    }

    /** Adds a new lost-item report. */
    public void addLostItem(LostItem item) {
        lostItems.add(item);
    }

    /** Searches for a report by item name. (Full search by category/location to be added.) */
    public LostItem searchLostItem(String itemName) {
        // TODO: implement full search (name, category, location)
        return null;
    }

    /** Updates an existing report. */
    public void updateLostItem(LostItem item) {
        // TODO: implement update logic
    }

    /** Deletes a report by its ID. Returns true if deleted. */
    public boolean deleteLostItem(int itemId) {
        // TODO: implement delete logic
        return false;
    }

    /** Prints all reports to the console. */
    public void displayLostItems() {
        for (LostItem item : lostItems) {
            System.out.println(item.printDetails());
        }
    }
}
