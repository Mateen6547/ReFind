/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package claimsrecovery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

// The GUI for the Claims / Recovery section.
// Four tabs: Submit Claim, Review Claims, Recover Unclaimed Item, Tracking.
public class ClaimsRecoveryGUI extends JFrame {

    private ClaimsRecovery manager;
    private DefaultTableModel claimModel;
    private DefaultTableModel recoveryModel;
    private JTable claimTable;
    private JTable recoveryTable;

    // Constructor
    public ClaimsRecoveryGUI() {
        manager = new ClaimsRecovery();

        setTitle("ReFind - Claims and Recovery");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Claim table
        claimModel = new DefaultTableModel(
                new String[]{"Claim ID", "Item ID", "Claimant",
                             "Evidence", "Status", "Decision"}, 0);
        claimTable = new JTable(claimModel);

        // Recovery table
        recoveryModel = new DefaultTableModel(
                new String[]{"Recovery ID", "Item ID", "Condition",
                             "Destination", "Handling"}, 0);
        recoveryTable = new JTable(recoveryModel);

        tabs.addTab("Submit a Claim", createSubmitClaimPanel());
        tabs.addTab("Review and Manage Claims", createReviewPanel());
        tabs.addTab("Unclaimed Item Recovery", createRecoveryPanel());
        tabs.addTab("Tracking", createTrackingPanel());

        add(tabs, BorderLayout.CENTER);
        refreshTables();
    }

    //  Tab 1: Submit a claim 
    private JPanel createSubmitClaimPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));

        final JTextField claimIdField = new JTextField();
        final JTextField itemIdField = new JTextField();
        final JTextField claimantField = new JTextField();
        final JTextArea evidenceArea = new JTextArea(4, 25);
        evidenceArea.setLineWrap(true);
        evidenceArea.setWrapStyleWord(true);

        form.add(new JLabel("Claim ID:"));
        form.add(claimIdField);
        form.add(new JLabel("Found Item ID:"));
        form.add(itemIdField);
        form.add(new JLabel("Claimant name:"));
        form.add(claimantField);
        form.add(new JLabel("Ownership evidence:"));
        form.add(new JScrollPane(evidenceArea));

        JButton submit = new JButton("Submit Claim");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int claimId = Integer.parseInt(claimIdField.getText().trim());
                    int itemId = Integer.parseInt(itemIdField.getText().trim());

                    manager.addClaim(claimId, itemId,
                            claimantField.getText(),
                            evidenceArea.getText());

                    JOptionPane.showMessageDialog(null,
                            "Claim submitted and pending review.");

                    claimIdField.setText("");
                    itemIdField.setText("");
                    claimantField.setText("");
                    evidenceArea.setText("");
                    refreshTables();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Claim ID and Item ID must be numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(titlePanel("Submit a claim",
                "Enter the found item's ID and evidence of ownership."),
                BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(submit, BorderLayout.SOUTH);
        return panel;
    }

    //  Tab 2: Review and manage claims 
    private JPanel createReviewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

        final JTextField searchField = new JTextField(8);
        JButton searchBtn = new JButton("Search Claim ID");
        JButton showAllBtn = new JButton("Show All");
        JButton approveBtn = new JButton("Approve Selected");
        JButton rejectBtn = new JButton("Reject Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        // Search button
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(searchField.getText().trim());
                    Claim c = manager.findClaimById(id);
                    if (c == null) {
                        JOptionPane.showMessageDialog(null,
                                "No claim with that ID.");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                c.getRecordDetails());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Enter a valid claim ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show all
        showAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTables();
            }
        });

        // Approve
        approveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decideSelectedClaim(true);
            }
        });

        // Reject
        rejectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decideSelectedClaim(false);
            }
        });

        // Delete
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedClaim();
            }
        });

        controls.add(new JLabel("Claim ID:"));
        controls.add(searchField);
        controls.add(searchBtn);
        controls.add(showAllBtn);
        controls.add(approveBtn);
        controls.add(rejectBtn);
        controls.add(deleteBtn);

        panel.add(titlePanel("Review claims",
                "Select a claim and use the buttons to decide or delete."),
                BorderLayout.NORTH);
        panel.add(new JScrollPane(claimTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
        return panel;
    }

    //  Tab 3: Process unclaimed items 
    private JPanel createRecoveryPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));

        final JTextField recoveryIdField = new JTextField();
        final JTextField itemIdField = new JTextField();
        final JComboBox conditionBox = new JComboBox(
                new String[]{"Undamaged", "Damaged"});

        form.add(new JLabel("Recovery ID:"));
        form.add(recoveryIdField);
        form.add(new JLabel("Found Item ID:"));
        form.add(itemIdField);
        form.add(new JLabel("Item condition:"));
        form.add(conditionBox);

        JButton processBtn = new JButton("Process Unclaimed Item");
        processBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int recoveryId = Integer.parseInt(
                            recoveryIdField.getText().trim());
                    int itemId = Integer.parseInt(
                            itemIdField.getText().trim());
                    String condition =
                            conditionBox.getSelectedItem().toString();

                    Recovery r = manager.processUnclaimedItem(
                            recoveryId, itemId, condition);

                    JOptionPane.showMessageDialog(null,
                            "Processed. Destination: " + r.getDestination());

                    recoveryIdField.setText("");
                    itemIdField.setText("");
                    refreshTables();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "IDs must be numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton deleteBtn = new JButton("Delete Selected Recovery");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRecovery();
            }
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(processBtn);
        bottom.add(deleteBtn);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.add(form, BorderLayout.NORTH);
        content.add(new JScrollPane(recoveryTable), BorderLayout.CENTER);

        panel.add(titlePanel("Unclaimed item handling",
                "Undamaged items go to reuse. Damaged items go to recycling."),
                BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    //  Tab 4: Tracking 
    private JPanel createTrackingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        final JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JButton refreshBtn = new JButton("Refresh Tracking Information");
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("");
                ArrayList<String> details = manager.getAllTrackingDetails();
                if (details.size() == 0) {
                    output.setText("No claims or recoveries yet.");
                } else {
                    for (int i = 0; i < details.size(); i++) {
                        output.append(details.get(i) + "\n\n");
                    }
                }
            }
        });

        panel.add(titlePanel("Track claims and recovery",
                "View current status of claims and processed items."),
                BorderLayout.NORTH);
        panel.add(new JScrollPane(output), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }

    // The heading and description block.
    private JPanel titlePanel(String heading, String description) {
        JPanel p = new JPanel(new BorderLayout(4, 4));
        JLabel title = new JLabel(heading);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        JLabel sub = new JLabel(description);
        sub.setForeground(Color.DARK_GRAY);
        p.add(title, BorderLayout.NORTH);
        p.add(sub, BorderLayout.SOUTH);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        return p;
    }

    // Approve or reject the selected claim.
    private void decideSelectedClaim(boolean approve) {
        int row = claimTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Select a claim first.");
            return;
        }
        int claimId = Integer.parseInt(
                claimModel.getValueAt(row, 0).toString());
        String prompt;
        if (approve) {
            prompt = "Enter approval note:";
        } else {
            prompt = "Enter reason for rejection:";
        }
        String note = JOptionPane.showInputDialog(prompt);
        if (note == null) {
            return;
        }
        try {
            if (approve) {
                manager.approveClaim(claimId, note);
                JOptionPane.showMessageDialog(null, "Claim approved.");
            } else {
                manager.rejectClaim(claimId, note);
                JOptionPane.showMessageDialog(null, "Claim rejected.");
            }
            refreshTables();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete the selected claim.
    private void deleteSelectedClaim() {
        int row = claimTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Select a claim first.");
            return;
        }
        int id = Integer.parseInt(claimModel.getValueAt(row, 0).toString());
        int answer = JOptionPane.showConfirmDialog(null,
                "Delete claim #" + id + "?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                boolean removed = manager.deleteClaim(id);
                if (!removed) {
                    JOptionPane.showMessageDialog(null, "Claim not found.");
                }
                refreshTables();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Delete the selected recovery.
    private void deleteSelectedRecovery() {
        int row = recoveryTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Select a recovery first.");
            return;
        }
        int id = Integer.parseInt(
                recoveryModel.getValueAt(row, 0).toString());
        int answer = JOptionPane.showConfirmDialog(null,
                "Delete recovery #" + id + "?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            manager.deleteRecovery(id);
            refreshTables();
        }
    }

    // Refresh both tables from the manager.
    private void refreshTables() {
        // Clear and re-add claim rows.
        claimModel.setRowCount(0);
        ArrayList<Claim> claims = manager.getClaims();
        for (int i = 0; i < claims.size(); i++) {
            Claim c = claims.get(i);
            Object[] row = new Object[6];
            row[0] = c.getClaimId();
            row[1] = c.getItemId();
            row[2] = c.getClaimantName();
            row[3] = c.getOwnershipEvidence();
            row[4] = c.getStatus();
            row[5] = c.getDecisionNote();
            claimModel.addRow(row);
        }

        // Clear and re-add recovery rows.
        recoveryModel.setRowCount(0);
        ArrayList<Recovery> recoveries = manager.getRecoveries();
        for (int i = 0; i < recoveries.size(); i++) {
            Recovery r = recoveries.get(i);
            Object[] row = new Object[5];
            row[0] = r.getRecoveryId();
            row[1] = r.getItemId();
            row[2] = r.getItemCondition();
            row[3] = r.getDestination();
            row[4] = r.getHandlingNote();
            recoveryModel.addRow(row);
        }
    }

    // Challenging myself whilstb making by testing using these codes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClaimsRecoveryGUI gui = new ClaimsRecoveryGUI();
                gui.setVisible(true);
            }
        });
    }
}