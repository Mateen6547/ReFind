/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package refind;

/**
 *
 * @author mateen
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

// 
import claimsrecovery.ClaimsRecoveryGUI;
import founditems.FoundItemGUI;
import lostitems.LostItemGUI;

public class ReFindApp extends JFrame {

    public ReFindApp() {
        setTitle("ReFind - Sustainable Lost and Found");
        setSize(600, 420);
        setLocationRelativeTo(null);
        // Closing the menu closes the whole app.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("ReFind", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(java.awt.Font.BOLD, 32f));

        JLabel subtitle = new JLabel(
                "Sustainable Lost and Found Recovery and Recycling",
                SwingConstants.CENTER);
        subtitle.setForeground(Color.DARK_GRAY);

        JButton lostBtn = new JButton("Lost Items");
        JButton foundBtn = new JButton("Found Items");
        JButton claimsBtn = new JButton("Claims and Recovery");

        // Open each section in its own window.
        lostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LostItemGUI gui = new LostItemGUI();
                gui.setVisible(true);
            }
        });

        foundBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FoundItemGUI gui = new FoundItemGUI();
                gui.setVisible(true);
            }
        });

        claimsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClaimsRecoveryGUI gui = new ClaimsRecoveryGUI();
                gui.setVisible(true);
            }
        });

        JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
        buttons.add(lostBtn);
        buttons.add(foundBtn);
        buttons.add(claimsBtn);
        buttons.setBorder(
                BorderFactory.createEmptyBorder(20, 80, 20, 80));

        add(title, BorderLayout.NORTH);
        add(subtitle, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    // The one main method for the whole project.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ReFindApp app = new ReFindApp();
                app.setVisible(true);
            }
        });
    }
}