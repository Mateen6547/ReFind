/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claimsrecovery;

/**
 *
 * @author mateen
 */
// Represents one claim for one found item.
// Inherits from ClaimRecord and implements Trackable.

import java.io.Serializable;

// Represents what happens to an item nobody claimed.
// Undamaged items go to reuse. Damaged items go to recycling.
public class Recovery implements Serializable, Trackable {

    // The only two destinations.
    public enum Destination {
        HANDOVER_FOR_REUSE,
        RECYCLING
    }

    private int recoveryId;
    private int itemId;
    private String itemCondition;
    private Destination destination;
    private String handlingNote;

    // Constructor
    public Recovery(int recoveryId, int itemId, String itemCondition) {

        if (recoveryId <= 0 || itemId <= 0) {
            throw new IllegalArgumentException("IDs must be positive numbers.");
        }
        if (itemCondition == null || itemCondition.trim().equals("")) {
            throw new IllegalArgumentException("Condition cannot be empty.");
        }

        // Lowercase so "Damaged" and "damaged" both work.
        String condition = itemCondition.trim().toLowerCase();

        if (!condition.equals("damaged") && !condition.equals("undamaged")) {
            throw new IllegalArgumentException("Condition must be damaged or undamaged.");
        }

        this.recoveryId = recoveryId;
        this.itemId = itemId;
        this.itemCondition = condition;

        // Decide where the item goes.
        if (condition.equals("undamaged")) {
            this.destination = Destination.HANDOVER_FOR_REUSE;
            this.handlingNote = "Suitable for handover or reuse.";
        } else {
            this.destination = Destination.RECYCLING;
            this.handlingNote = "Sent for recycling or disposal.";
        }
    }

    // Getters
    public int getRecoveryId() {
        return recoveryId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public Destination getDestination() {
        return destination;
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    // Tracking line (from the Trackable interface).
    @Override
    public String getTrackingDetails() {
        return "Recovery #" + recoveryId + " for item #" + itemId
                + " | " + itemCondition + " | Destination: " + destination;
    }

    @Override
    public String toString() {
        return getTrackingDetails() + " | " + handlingNote;
    }
}