/*
 * Source: https://www.youtube.com/watch?v=oA_kcVaJQ3E
 */
package gomoku;

import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;

/**
 *
 * @author heyitsjeoff
 */
public class LobbyView extends javax.swing.JFrame {

    /**
     * Creates new form Lobby
     */
    public LobbyView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        onlineUsersLabel = new javax.swing.JLabel();
        challengeBTN = new javax.swing.JButton();
        aiBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        pendingLabel = new javax.swing.JLabel();
        outgoingLabel = new javax.swing.JLabel();
        acceptPendingBT = new javax.swing.JButton();
        declinePendingBT = new javax.swing.JButton();
        revokeBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        onlineList = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        incomingList = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        outgoingRequests = new javax.swing.JList();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUMOKU");
        setBackground(new java.awt.Color(255, 255, 255));

        onlineUsersLabel.setText("Online Users");

        challengeBTN.setText("Challenge Player");

        aiBTN.setText("Play AI");

        jLabel1.setText("Your Stats");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(14);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel2.setText("Selected Player's Stats");

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(14);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        pendingLabel.setText("Pending Requests");

        outgoingLabel.setText("Outgoing Requests");

        acceptPendingBT.setText("Accept");

        declinePendingBT.setText("Decline");

        revokeBT.setText("Revoke");

        jScrollPane1.setViewportView(onlineList);

        jScrollPane6.setViewportView(incomingList);

        jScrollPane7.setViewportView(outgoingRequests);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineUsersLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acceptPendingBT)
                                .addGap(18, 18, 18)
                                .addComponent(declinePendingBT))
                            .addComponent(outgoingLabel)
                            .addComponent(pendingLabel)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(revokeBT))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(aiBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(challengeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineUsersLabel)
                    .addComponent(pendingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(acceptPendingBT)
                            .addComponent(declinePendingBT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outgoingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(revokeBT))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(challengeBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aiBTN)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(LobbyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LobbyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LobbyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LobbyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LobbyView().setVisible(true);
                //dlm = new DefaultListModel();
            }
        });
    }
    
    public void challengePlayerListener(ActionListener listenerForChallengeBTN){
        challengeBTN.addActionListener(listenerForChallengeBTN);
    }
    
    public void acceptListener(ActionListener listenerForAcceptBTN){
        acceptPendingBT.addActionListener(listenerForAcceptBTN);
    }
    
    public String getSelectedUsername(){
        return (String) onlineList.getSelectedValue();
    }
    
    public String getIncomingUsername(){
        return (String) incomingList.getSelectedValue();
    }
    
    public void updateOnlineList(DefaultListModel dlm){
        onlineList.setModel(dlm);
    }
    
    public void updateIncomingList(DefaultListModel idlm){
        incomingList.setModel(idlm);
    }

    //private static DefaultListModel dlm;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptPendingBT;
    private javax.swing.JButton aiBTN;
    private javax.swing.JButton challengeBTN;
    private javax.swing.JButton declinePendingBT;
    private javax.swing.JList incomingList;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JList onlineList;
    private javax.swing.JLabel onlineUsersLabel;
    private javax.swing.JLabel outgoingLabel;
    private javax.swing.JList outgoingRequests;
    private javax.swing.JLabel pendingLabel;
    private javax.swing.JButton revokeBT;
    // End of variables declaration//GEN-END:variables
}
