/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jona622
 */
public class loginFrame extends javax.swing.JFrame {

    private String emailUsed;

    /**
     * Creates new form loginFrame
     */
    public loginFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public String getEmailUsed() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Data/emailUsed.txt"));
            emailUsed = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(loginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(loginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return emailUsed;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitButton = new javax.swing.JButton();
        loginIconLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        newAccountLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exitButton.setBackground(java.awt.Color.blue);
        exitButton.setFont(new java.awt.Font("Noto Sans", 1, 14)); // NOI18N
        exitButton.setForeground(java.awt.Color.white);
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 640, 110, -1));

        loginIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/login_1.png"))); // NOI18N
        getContentPane().add(loginIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 320, 300));

        emailLabel.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        emailLabel.setText("Email:");
        getContentPane().add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        passwordLabel.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        passwordLabel.setText("Password:");
        getContentPane().add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        emailField.setBackground(new java.awt.Color(0, 92, 234));
        emailField.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
        emailField.setForeground(new java.awt.Color(254, 254, 254));
        emailField.setMaximumSize(new java.awt.Dimension(6, 26));
        getContentPane().add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 260, -1));

        passwordField.setBackground(new java.awt.Color(0, 92, 234));
        passwordField.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
        passwordField.setForeground(new java.awt.Color(254, 254, 254));
        passwordField.setMaximumSize(new java.awt.Dimension(6, 26));
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 260, 30));

        loginButton.setBackground(java.awt.Color.blue);
        loginButton.setFont(new java.awt.Font("Noto Sans", 1, 14)); // NOI18N
        loginButton.setForeground(new java.awt.Color(254, 254, 254));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 90, -1));

        newAccountLabel.setForeground(java.awt.Color.black);
        newAccountLabel.setText("Create new account.");
        newAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newAccountLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newAccountLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newAccountLabelMouseEntered(evt);
            }
        });
        getContentPane().add(newAccountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, -1, -1));

        backgroundLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/bg.jpg"))); // NOI18N
        backgroundLabel.setMaximumSize(new java.awt.Dimension(450, 680));
        backgroundLabel.setMinimumSize(new java.awt.Dimension(450, 680));
        backgroundLabel.setPreferredSize(new java.awt.Dimension(450, 680));
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        String email = "email:" + emailField.getText() + " ";
        String pass = "pass:" + passwordField.getText() + " ";
        File file = new File("./Data/users.txt");
        BufferedReader br;
        BufferedWriter bw;
        String line;
        String em = "";
        String ps = "";
        String id = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                em = line.substring(line.indexOf("email:"), line.indexOf("pass"));
                ps = line.substring(line.indexOf("pass:"), line.indexOf("phone:"));
                System.out.println("Em: " + em + " ps: " + ps);
                if (email.equals(em) && pass.equals(ps)) {
                    JOptionPane.showMessageDialog(this, "Sucessful login.", "Sucessful login.", JOptionPane.INFORMATION_MESSAGE);
                    //open the main frame and close tis
                    Main2Frame main = new Main2Frame();
                    main.setVisible(true);
                    this.dispose();
                    emailUsed = em;
                    bw = new BufferedWriter(new FileWriter("./Data/emailUsed.txt"));
                    bw.write(emailUsed);
                    bw.close();
                    //
                    break;
                } else if((id = br.readLine()) == null){
                    JOptionPane.showMessageDialog(this, "Error: The email/password doesn't match.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: The email/password doesn't match.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void newAccountLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAccountLabelMouseEntered
        // TODO add your handling code here:
        newAccountLabel.setForeground(Color.white);
        Font font = newAccountLabel.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        newAccountLabel.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_newAccountLabelMouseEntered

    private void newAccountLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAccountLabelMouseExited
        // TODO add your handling code here:
        newAccountLabel.setForeground(Color.black);
        Font font = newAccountLabel.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, null);
        newAccountLabel.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_newAccountLabelMouseExited

    private void newAccountLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newAccountLabelMouseClicked
        // TODO add your handling code here:
        newAccFrame acc = new newAccFrame();
        acc.setVisible(true);
    }//GEN-LAST:event_newAccountLabelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginIconLabel;
    private javax.swing.JLabel newAccountLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}
