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
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import queues.Node;
import queues.Queue;

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
                    recieveET.setText(msg);
                    if (msg.toLowerCase().contains("aiuda")) {
                        if(msg.toLowerCase().contains("mov") && alarma){
                        
                        }
                        if(msg.toLowerCase().contains("smoke")){
                            
                        }

                    } else if (!(msg.toLowerCase().contains("calib"))) {
                        String h = msg.substring(msg.indexOf("H") + 1, msg.indexOf(" T"));
                        humQ.addNode(new Node(h));
                        String t = msg.substring(msg.indexOf("T") + 1, msg.indexOf(" S"));
                        tempQ.addNode(new Node(t));
                        String s = msg.substring(msg.indexOf("S") + 1, msg.indexOf(" M"));
                        gasQ.addNode(new Node(s));
                        String m = msg.substring(msg.indexOf("M") + 1);
                        movQ.addNode(new Node(m));
                        i++;
                        humXY.add(i, Integer.parseInt(h));
                        tempXY.add(i, Integer.parseInt(t));
                        gasXY.add(i, Integer.parseInt(s));
                        movXY.add(i, Integer.parseInt(m));
                    }

                    if (humQ.length() > 1000) {
                        saveData(humQ, 0);
                    }
                    if (tempQ.length() > 1000) {
                        saveData(tempQ, 1);
                    }
                    if (gasQ.length() > 1000) {
                        saveData(gasQ, 2);
                    }
                    if (movQ.length() > 1000) {
                        saveData(movQ, 3);
                    }

                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(Main2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    public void saveData(Queue q, int i) {
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
            bw.write("" + prom + " 1000 \n");
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void notifyUser(){
        
    }

    public Main2Frame() {
        initComponents();
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
        alarmBTN = new javax.swing.JButton();
        exitBTN = new javax.swing.JButton();
        bgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recieveET.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        recieveET.setText("Test");
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
        jLabel1.setText("Puerto:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        restartBTN.setText("Restart");
        restartBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartBTNActionPerformed(evt);
            }
        });
        getContentPane().add(restartBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(419, 80, 90, -1));

        humPanel.setBackground(new java.awt.Color(0, 128, 255));
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

        tempPanel.setBackground(new java.awt.Color(0, 128, 255));
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

        gasPanel.setBackground(new java.awt.Color(0, 128, 255));
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

        movPanel.setBackground(new java.awt.Color(0, 128, 255));
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

        alarmBTN.setText("Activate Alarm.");
        alarmBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alarmBTNActionPerformed(evt);
            }
        });
        getContentPane().add(alarmBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 120, -1));

        exitBTN.setText("Exit");
        exitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBTNActionPerformed(evt);
            }
        });
        getContentPane().add(exitBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, -1));

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
        System.out.println("xdxdx");
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

    private void alarmBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alarmBTNActionPerformed
        // TODO add your handling code here:
        alarma = true;
        System.out.println("Humidity queue");
        System.out.println(humQ.length());
        System.out.println("Temperature queue");
        System.out.println(tempQ.length());
        System.out.println("Smoke queue");
        System.out.println(gasQ.length());
        System.out.println("Movement queue");
        System.out.println(movQ.length());
    }//GEN-LAST:event_alarmBTNActionPerformed

    private void exitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitBTNActionPerformed

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
    private javax.swing.JButton alarmBTN;
    private javax.swing.JLabel bgLabel;
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
