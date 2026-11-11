/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claimsrecovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Manages all the claims and recoveries. Saves and laod them from a file.
public class ClaimsRecovery {

    private static final String DATA_FILE = "refind_claims_recovery.dat";

    // Lists of all the claims and recoveries.
    private ArrayList<Claim> claims;
    private ArrayList<Recovery> recoveries;

    // Constructor
    public ClaimsRecovery() {
        claims = new ArrayList<Claim>();
        recoveries = new ArrayList<Recovery>();
        loadFromFile();
    }

    // Get all claims.
    public ArrayList<Claim> getClaims() {
        return claims;
    }

    // Get all recoveries.
    public ArrayList<Recovery> getRecoveries() {
        return recoveries;
    }

    // Search for finding a claim by ID.
    public Claim findClaimById(int claimId) {
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getClaimId() == claimId) {
                return claims.get(i);
            }
        }
        return null;
    }

    // Search for finding every claim for a particular item.
    public ArrayList<Claim> searchClaimsByItemId(int itemId) {
        ArrayList<Claim> matches = new ArrayList<Claim>();
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getItemId() == itemId) {
                matches.add(claims.get(i));
            }
        }
        return matches;
    }

    // Adding a new claim.
    public Claim addClaim(int claimId, int itemId, String claimantName,
                          String evidence) {
        if (findClaimById(claimId) != null) {
            throw new IllegalArgumentException("That claim ID is already used.");
        }
        Claim c = new Claim(claimId, itemId, claimantName, evidence);
        claims.add(c);
        saveToFile();
        return c;
    }

    // Delete/ remove a claim.
    public boolean deleteClaim(int claimId) {
        Claim c = findClaimById(claimId);
        if (c == null) {
            return false;
        }
        if (c.getStatusValue() == Claim.Status.APPROVED) {
            throw new IllegalArgumentException("Approved claims cannot be deleted.");
        }
        claims.remove(c);
        saveToFile();
        return true;
    }

    // Approve a claim.
    public void approveClaim(int claimId, String note) {
        Claim c = findClaimById(claimId);
        if (c == null) {
            throw new IllegalArgumentException("Claim not found.");
        }
        // Make sure no other claim for the same item is already approved.
        for (int i = 0; i < claims.size(); i++) {
            Claim other = claims.get(i);
            if (other.getItemId() == c.getItemId()
                    && other.getStatusValue() == Claim.Status.APPROVED) {
                throw new IllegalArgumentException("Another claim is already approved.");
            }
        }
        c.approve(note);
        saveToFile();
    }

    // Reject a claim.
    public void rejectClaim(int claimId, String note) {
        Claim c = findClaimById(claimId);
        if (c == null) {
            throw new IllegalArgumentException("Claim not found.");
        }
        c.reject(note);
        saveToFile();
    }

    // Process an unclaimed item.
    public Recovery processUnclaimedItem(int recoveryId, int itemId,
                                         String condition) {
        // Do not allow two recoveries for the same item.
        for (int i = 0; i < recoveries.size(); i++) {
            if (recoveries.get(i).getItemId() == itemId) {
                throw new IllegalArgumentException("This item already has a recovery record.");
            }
        }

        // Do not allow recovery if there is a pending or approved claim.
        for (int i = 0; i < claims.size(); i++) {
            Claim c = claims.get(i);
            if (c.getItemId() == itemId
                    && c.getStatusValue() == Claim.Status.APPROVED) {
                throw new IllegalArgumentException("Item already claimed.");
            }
            if (c.getItemId() == itemId
                    && c.getStatusValue() == Claim.Status.PENDING) {
                throw new IllegalArgumentException("Item has a pending claim.");
            }
        }

        Recovery r = new Recovery(recoveryId, itemId, condition);
        recoveries.add(r);
        saveToFile();
        return r;
    }

    // Find a recovery by ID.
    public Recovery findRecoveryById(int recoveryId) {
        for (int i = 0; i < recoveries.size(); i++) {
            if (recoveries.get(i).getRecoveryId() == recoveryId) {
                return recoveries.get(i);
            }
        }
        return null;
    }

    // Delete a recovery.
    public boolean deleteRecovery(int recoveryId) {
        Recovery r = findRecoveryById(recoveryId);
        if (r == null) {
            return false;
        }
        recoveries.remove(r);
        saveToFile();
        return true;
    }

    // Get tracking text for every claim and recovery.
    public ArrayList<String> getAllTrackingDetails() {
        ArrayList<String> details = new ArrayList<String>();

        for (int i = 0; i < claims.size(); i++) {
            details.add(claims.get(i).getTrackingDetails());
        }
        for (int i = 0; i < recoveries.size(); i++) {
            details.add(recoveries.get(i).getTrackingDetails());
        }
        return details;
    }

    // Save both lists to a file.
    private void saveToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(DATA_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(claims);
            out.writeObject(recoveries);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    // Load both lists from a file (if it exists).
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            claims = (ArrayList<Claim>) in.readObject();
            recoveries = (ArrayList<Recovery>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("Could not load: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load: " + e.getMessage());
        }
    }
}