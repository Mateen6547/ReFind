/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claimsrecovery;

/**
 *
 * @author mateen
 *
 *Represents one claim for one found item.
 *Inherits from ClaimRecord and implements Trackable.
*/
public class Claim extends ClaimRecord implements Trackable {

    // A claim can only be one of these four statuses.
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELLED
    }

    private String ownershipEvidence;
    private Status status;
    private String decisionNote;

    // Constructor
    public Claim(int claimId, int itemId, String claimantName,
                 String ownershipEvidence) {

        // Send shared values to the parent class.
        super(claimId, itemId, claimantName);

        if (ownershipEvidence == null || ownershipEvidence.trim().equals("")) {
            throw new IllegalArgumentException("Evidence cannot be empty.");
        }

        this.ownershipEvidence = ownershipEvidence.trim();
        this.status = Status.PENDING;
        this.decisionNote = "Awaiting review";
    }

    // Getters
    public String getOwnershipEvidence() {
        return ownershipEvidence;
    }

    public Status getStatusValue() {
        return status;
    }

    public String getDecisionNote() {
        return decisionNote;
    }

    // Approve the claim. .
    public void approve(String note) {
        if (status != Status.PENDING) {
            throw new IllegalArgumentException("Only pending claims can be approved.");//Only allowed if it is still pending
        }
        status = Status.APPROVED;
        if (note == null || note.trim().equals("")) {
            decisionNote = "Claim approved.";
        } else {
            decisionNote = note.trim();
        }
    }

    // Reject the claim.
    public void reject(String note) {
        if (status != Status.PENDING) {
            throw new IllegalArgumentException("Only pending claims can be rejected.");//Only allowed if it is still pending.

        }
        status = Status.REJECTED;
        if (note == null || note.trim().equals("")) {
            decisionNote = "Claim rejected.";
        } else {
            decisionNote = note.trim();
        }
    }

    // Cancel the claim. Only allowed if it is still pending.
    public void cancel() {
        if (status != Status.PENDING) {
            throw new IllegalArgumentException("Only pending claims can be cancelled.");
        }
        status = Status.CANCELLED;
        decisionNote = "Claim cancelled.";
    }

    // Full description of this claim.
    @Override
    public String getRecordDetails() {
        return "Claim #" + getClaimId() + " | Item #" + getItemId()
                + " | Claimant: " + getClaimantName()
                + " | Status: " + status
                + " | Evidence: " + ownershipEvidence
                + " | Decision: " + decisionNote;
    }

    // Status as text.
    @Override
    public String getStatus() {
        return status.name();
    }

    // The tracking line.
    @Override
    public String getTrackingDetails() {
        return "Claim #" + getClaimId() + " for item #" + getItemId()
                + " is " + status + ". " + decisionNote;
    }

    @Override
    public String toString() {
        return getRecordDetails();
    }
}