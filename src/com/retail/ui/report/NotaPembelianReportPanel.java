/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NotaPembelianReportPanel.java
 *
 * Created on 05 Agu 11, 9:28:35
 */
package com.retail.ui.report;

import com.retail.main.Main;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Hauw
 */
public class NotaPembelianReportPanel extends javax.swing.JDialog {

    /** Creates new form NotaPembelianReportPanel */
    public NotaPembelianReportPanel(String idPembelian) {
        super(Main.getFrameUtama(), true);
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
        showReport(idPembelian);
    }

    private void showReport(String idPembelian) {
        JRViewer viewer = new JRViewer(Main.getReportService().getNotaPembelianReport(idPembelian));
        viewer.setSize(panelReport.getSize());
        panelReport.add(viewer);
        viewer.setVisible(true);
        panelReport.updateUI();
        setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelReport = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelReport.setName("panelReport"); // NOI18N
        panelReport.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelReport, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelReport;
    // End of variables declaration//GEN-END:variables
}
