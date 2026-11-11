/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claimsrecovery;

import java.io.Serializable;

// Abstract class cus we cannot create an object from it directly.
//Holds the details that every claim record shares.
public abstract class ClaimRecord implements Serializable {

    // Shared details
    private int claimId;
    private int itemId;
    private String claimantName;

    // Constructor
    public ClaimRecord(int claimId, int itemId, String claimantName) {
        if (claimId <= 0 || itemId <= 0) {
            throw new IllegalArgumentException("IDs must be positive numbers.");
        }
        if (claimantName == null || claimantName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.claimId = claimId;
        this.itemId = itemId;
        this.claimantName = claimantName.trim();
    }

    // Getters
    public int getClaimId() {
        return claimId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getClaimantName() {
        return claimantName;
    }

    //Methods.
    public abstract String getRecordDetails();
    public abstract String getStatus();
}