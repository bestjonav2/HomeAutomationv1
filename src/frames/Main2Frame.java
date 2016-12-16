package frames;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import code.Node;
import code.Queue;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Jona622
 */
public class Main2Frame extends javax.swing.JFrame {

    PanamaHitek_Arduino arduinoConnection = new PanamaHitek_Arduino();
    String portName;
    Queue humQ, tempQ, gasQ, movQ;
    XYSeries humXY = new XYSeries("Humidity");
    XYSeriesCollection humColl = new XYSeriesCollection();
    JFreeChart humGraf;
    XYSeries tempXY = new XYSeries("Temperature");
    XYSeriesCollection tempColl = new XYSeriesCollection();
    JFreeChart tempGraf;
    XYSeries gasXY = new XYSeries("Smoke");
    XYSeriesCollection gasColl = new XYSeriesCollection();
    JFreeChart gasGraf;
    XYSeries movXY = new XYSeries("Movement");
    XYSeriesCollection movColl = new XYSeriesCollection();
    JFreeChart movGraf;
    int i = 0;
    String[] rutas = {"./Data/infoH.txt", "./Data/infoT.txt", "./Data/infoS.txt", "./Data/infoM.txt"};
    File archivo;
    BufferedWriter bw = null;
    BufferedReader br = null;
    boolean alarma = false;
    SerialPortEventListener humEv = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (arduinoConnection.isMessageAvailable()) {
                    String msg = arduinoConnection.printMessage();
                    String m = "", s = "", h = "", t = "";
//                    recieveET.setText(msg);
                    if (!(msg.toLowerCase().contains("calib"))) {
                        if (msg.contains("H") && msg.contains("T") && msg.contains("S") && msg.contains("M")) {
                            h = msg.substring(msg.indexOf("H") + 1, msg.indexOf(" T"));
                            t = msg.substring(msg.indexOf("T") + 1, msg.indexOf(" S"));
                            s = msg.substring(msg.indexOf("S") + 1, msg.indexOf(" M"));
                            m = msg.substring(msg.indexOf("M") + 1);
                            if (msg.toLowerCase().contains("aiuda")) {
//                            System.out.println("aiudaaaaaaaaaaa-------------------");
//                            System.out.println(alarma);
                                if (msg.toLowerCase().contains("mov")) {
                                    m = msg.substring(msg.indexOf("M") + 1, msg.indexOf("Aiudaaaa!"));
                                }
                                if (msg.toLowerCase().contains("mov") && alarma == true) {
                                    m = msg.substring(msg.indexOf("M") + 1, msg.indexOf("Aiudaaaa!"));
                                    System.out.println(" Sending msg");
                                    notifyUser("We have detected movement in your house.");
                                }
                                if (msg.toLowerCase().contains("smoke")) {
                                    s = msg.substring(msg.indexOf("S") , msg.indexOf("Aiudaaaa!"));
                                    System.out.println("Smoke  "+s);
                                    //notifyUser("We have detected high concentrations of smoke in your home.");
                                    s = s.substring(1,s.indexOf(" M0"));
                                    System.out.println("Smoke  "+s);
                                    m = "0";
                                }

                            }
                            humQ.addNode(new Node(h));
                            tempQ.addNode(new Node(t));
                            gasQ.addNode(new Node(s));
                            movQ.addNode(new Node(m));
                            i++;
                            humXY.add(i, Integer.parseInt(h));
                            tempXY.add(i, Integer.parseInt(t));
                            gasXY.add(i, Integer.parseInt(s));
                            movXY.add(i, Integer.parseInt(m));
                            recieveET.setText("Humidity: " + h + "%, Temperature: " + t + " celsius, Smoke: " + s + " ppm, Movement: " + m);

                        }
                    } else {
                        recieveET.setText(msg);
//                        msg = "";
                    }

                    if (humQ.length() > 1000) {
                        saveData(humQ, 0, 1000);
                    }
                    if (tempQ.length() > 1000) {
                        saveData(tempQ, 1, 1000);
                    }
                    if (gasQ.length() > 1000) {
                        saveData(gasQ, 2, 1000);
                    }
                    if (movQ.length() > 1000) {
                        saveData(movQ, 3, 1000);
                    }

                }

            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(Main2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    public void saveData(Queue q, int i, int intervalo) {
        float prom = 0;
        try {
            for (int j = 0; j < q.length(); j++) {
                try {
                    prom += Float.parseFloat(q.readNode());
//                    System.out.println(prom);
                } catch (Exception ex) {
                    Logger.getLogger(Main2Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            prom = prom / q.length();
//            System.out.println(prom);
            archivo = new File(rutas[i]);
            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write("" + prom + " " + intervalo + " \n");
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyUser(String msg) {
        final String username = "m.jona622@gmail.com";
        final String password = ".Gmail123";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            String aux;
            String x = new loginFrame().getEmailUsed();
            String em = "";
            try {
                br = new BufferedReader(new FileReader(new File("./Data/users.txt")));
                while ((aux = br.readLine()) != null) {
                    em = aux.substring(aux.indexOf("email:"), aux.indexOf("pass"));
                    if (x.equalsIgnoreCase(em)) {
                        em = em.substring(em.indexOf("email:") + 6);
                        break;
                    }
                }
            } catch (HeadlessException | IOException e) {
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("m.jona622@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(em));
            message.setSubject("Alert: Something is happening now.");
            message.setText(msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public Main2Frame() {
        initComponents();
        this.setLocationRelativeTo(null);
        humQ = new Queue();
        tempQ = new Queue();
        gasQ = new Queue();
        movQ = new Queue();
        portName = "no_port";
        humXY.add(0, 0);
        humColl.addSeries(humXY);
        humGraf = ChartFactory.createXYLineChart("Humidity", "Time", "Humidity", humColl, PlotOrientation.VERTICAL, true, true, false);
        tempXY.add(0, 0);
        tempColl.addSeries(tempXY);
        tempGraf = ChartFactory.createXYLineChart("Temperature", "Time", "Temperature", tempColl, PlotOrientation.VERTICAL, true, true, false);
        gasXY.add(0, 0);
        gasColl.addSeries(gasXY);
        gasGraf = ChartFactory.createXYLineChart("Smoke", "Time", "Smoke", gasColl, PlotOrientation.VERTICAL, true, true, false);
        movXY.add(0, 0);
        movColl.addSeries(movXY);
        movGraf = ChartFactory.createXYLineChart("Movement", "Time", "Movement", movColl, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recieveET = new javax.swing.JTextField();
        setPortBTN = new javax.swing.JButton();
        portET = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        restartBTN = new javax.swing.JButton();
        humPanel = new javax.swing.JPanel();
        tempPanel = new javax.swing.JPanel();
        gasPanel = new javax.swing.JPanel();
        movPanel = new javax.swing.JPanel();
        aAlarmBTN = new javax.swing.JButton();
        exitBTN = new javax.swing.JButton();
        dAlarmBTN1 = new javax.swing.JButton();
        bgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recieveET.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        recieveET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recieveETActionPerformed(evt);
            }
        });
        getContentPane().add(recieveET, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 640, -1));

        setPortBTN.setText("Start");
        setPortBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPortBTNActionPerformed(evt);
            }
        });
        getContentPane().add(setPortBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 90, -1));

        portET.setText("/dev/ttyUSB0");
        getContentPane().add(portET, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 110, -1));

        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setText("Port:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        restartBTN.setText("Restart");
        restartBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartBTNActionPerformed(evt);
            }
        });
        getContentPane().add(restartBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(419, 80, 90, -1));

        humPanel.setBackground(new java.awt.Color(0, 135, 255));
        humPanel.setMaximumSize(new java.awt.Dimension(450, 262));
        humPanel.setMinimumSize(new java.awt.Dimension(450, 262));
        humPanel.setPreferredSize(new java.awt.Dimension(450, 262));

        javax.swing.GroupLayout humPanelLayout = new javax.swing.GroupLayout(humPanel);
        humPanel.setLayout(humPanelLayout);
        humPanelLayout.setHorizontalGroup(
            humPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        humPanelLayout.setVerticalGroup(
            humPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        getContentPane().add(humPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 126, -1, -1));

        tempPanel.setBackground(new java.awt.Color(0, 135, 255));
        tempPanel.setPreferredSize(new java.awt.Dimension(450, 262));

        javax.swing.GroupLayout tempPanelLayout = new javax.swing.GroupLayout(tempPanel);
        tempPanel.setLayout(tempPanelLayout);
        tempPanelLayout.setHorizontalGroup(
            tempPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        tempPanelLayout.setVerticalGroup(
            tempPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        getContentPane().add(tempPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 126, -1, -1));

        gasPanel.setBackground(new java.awt.Color(0, 135, 255));
        gasPanel.setPreferredSize(new java.awt.Dimension(450, 262));

        javax.swing.GroupLayout gasPanelLayout = new javax.swing.GroupLayout(gasPanel);
        gasPanel.setLayout(gasPanelLayout);
        gasPanelLayout.setHorizontalGroup(
            gasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        gasPanelLayout.setVerticalGroup(
            gasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        getContentPane().add(gasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 406, -1, -1));

        movPanel.setBackground(new java.awt.Color(0, 135, 255));
        movPanel.setPreferredSize(new java.awt.Dimension(450, 262));

        javax.swing.GroupLayout movPanelLayout = new javax.swing.GroupLayout(movPanel);
        movPanel.setLayout(movPanelLayout);
        movPanelLayout.setHorizontalGroup(
            movPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        movPanelLayout.setVerticalGroup(
            movPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        getContentPane().add(movPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 406, -1, -1));

        aAlarmBTN.setText("Activate Alarm.");
        aAlarmBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aAlarmBTNActionPerformed(evt);
            }
        });
        getContentPane().add(aAlarmBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 120, -1));

        exitBTN.setText("Exit");
        exitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBTNActionPerformed(evt);
            }
        });
        getContentPane().add(exitBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 120, -1));

        dAlarmBTN1.setText("Deactivate Alarm.");
        dAlarmBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dAlarmBTN1ActionPerformed(evt);
            }
        });
        getContentPane().add(dAlarmBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, -1));

        bgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/bg.jpg"))); // NOI18N
        getContentPane().add(bgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setPortBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPortBTNActionPerformed
        // TODO add your handling code here:
        portName = portET.getText();
        try {
            arduinoConnection.arduinoRX(portName, 9600, humEv);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(Main2Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        ChartPanel humCP = new ChartPanel(humGraf);
        humPanel.setLayout(new java.awt.BorderLayout());
        humPanel.add(humCP, BorderLayout.CENTER);
        humPanel.validate();
        ChartPanel tempCP = new ChartPanel(tempGraf);
        tempPanel.setLayout(new java.awt.BorderLayout());
        tempPanel.add(tempCP, BorderLayout.CENTER);
        tempPanel.validate();
        ChartPanel gasCP = new ChartPanel(gasGraf);
        gasPanel.setLayout(new java.awt.BorderLayout());
        gasPanel.add(gasCP, BorderLayout.CENTER);
        gasPanel.validate();
        ChartPanel movCP = new ChartPanel(movGraf);
        movPanel.setLayout(new java.awt.BorderLayout());
        movPanel.add(movCP, BorderLayout.CENTER);
        movPanel.validate();
    }//GEN-LAST:event_setPortBTNActionPerformed

    private void recieveETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recieveETActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recieveETActionPerformed

    private void restartBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartBTNActionPerformed
        try {
            // TODO add your handling code here:
            arduinoConnection.killArduinoConnection();
            setPortBTN.doClick();
        } catch (ArduinoException ex) {
            Logger.getLogger(Main2Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_restartBTNActionPerformed

    private void aAlarmBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aAlarmBTNActionPerformed
        // TODO add your handling code here:
        alarma = true;
//        System.out.println("Humidity queue");
//        System.out.println(humQ.length());
//        System.out.println("Temperature queue");
//        System.out.println(tempQ.length());
//        System.out.println("Smoke queue");
//        System.out.println(gasQ.length());
//        System.out.println("Movement queue");
//        System.out.println(movQ.length());
        System.out.println(alarma);

    }//GEN-LAST:event_aAlarmBTNActionPerformed

    private void exitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNActionPerformed
        // TODO add your handling code here:
        int longHum = humQ.length();
        int longTemp = tempQ.length();
        int longGas = gasQ.length();
        int longMov = movQ.length();
        saveData(humQ, 0, longHum);
        saveData(tempQ, 1, longTemp);
        saveData(gasQ, 2, longGas);
        saveData(movQ, 3, longMov);
        System.exit(0);
    }//GEN-LAST:event_exitBTNActionPerformed

    private void dAlarmBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dAlarmBTN1ActionPerformed
        // TODO add your handling code here:
        alarma = false;
        System.out.println(alarma);
    }//GEN-LAST:event_dAlarmBTN1ActionPerformed

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
            java.util.logging.Logger.getLogger(Main2Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main2Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main2Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main2Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main2Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aAlarmBTN;
    private javax.swing.JLabel bgLabel;
    private javax.swing.JButton dAlarmBTN1;
    private javax.swing.JButton exitBTN;
    private javax.swing.JPanel gasPanel;
    private javax.swing.JPanel humPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel movPanel;
    private javax.swing.JTextField portET;
    private javax.swing.JTextField recieveET;
    private javax.swing.JButton restartBTN;
    private javax.swing.JButton setPortBTN;
    private javax.swing.JPanel tempPanel;
    // End of variables declaration//GEN-END:variables
}
