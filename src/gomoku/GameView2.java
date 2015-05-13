/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author jeffthechef14
 */
public class GameView2 extends javax.swing.JFrame {

    private Cell[][] theBoard;
    private Cell theCell;
    private int row, col;
    private Color myColor = Color.blue;
    private Color enemyColor = Color.orange;
    private Color blank = Color.white;
    
    /**
     * Creates new form GameView2
     */
    public GameView2() {
        initComponents();
        this.getContentPane().setBackground(blank);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        boardPANEL = new javax.swing.JPanel();
        sendBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setMaximumSize(new java.awt.Dimension(910, 910));
        setMinimumSize(new java.awt.Dimension(910, 910));
        setPreferredSize(new java.awt.Dimension(910, 910));

        boardPANEL.setBackground(new java.awt.Color(40, 240, 240));
        boardPANEL.setMaximumSize(new java.awt.Dimension(420, 420));
        boardPANEL.setMinimumSize(new java.awt.Dimension(420, 420));
        boardPANEL.setPreferredSize(new java.awt.Dimension(420, 420));

        javax.swing.GroupLayout boardPANELLayout = new javax.swing.GroupLayout(boardPANEL);
        boardPANEL.setLayout(boardPANELLayout);
        boardPANELLayout.setHorizontalGroup(
            boardPANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        boardPANELLayout.setVerticalGroup(
            boardPANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        sendBTN.setBackground(new java.awt.Color(0, 255, 255));
        sendBTN.setFont(new java.awt.Font("Gotham Black", 0, 24)); // NOI18N
        sendBTN.setForeground(new java.awt.Color(255, 255, 255));
        sendBTN.setText("Send Move");

        jTextPane1.setEditable(false);
        jTextPane1.setBackground(new java.awt.Color(0, 255, 255));
        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Gotham Bold", 0, 48)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTextPane1.setText("  Gomoku");
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(boardPANEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(sendBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boardPANEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
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
            java.util.logging.Logger.getLogger(GameView2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameView2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameView2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameView2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameView2().setVisible(true);
            }
        });
    }
    
    public void drawBoard(int row, int col, ActionListener cellListener){
        
        this.row = row;
        this.col = col;
        theBoard = new Cell[row][col];
        this.boardPANEL.setLayout(new GridLayout(row, col));
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                theBoard[i][j] = new Cell(i,j);
                theBoard[i][j].cellListener(cellListener);
            }
        }
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                this.boardPANEL.add(theBoard[i][j]);
            }
        }
    }
    
    public void updateCell(int row, int col, Color theColor){
        theBoard[row][col].setBackground(theColor);
        theBoard[row][col].setEnabled(false);
    }
    
    public void clickCell(int row, int col){
        if(!theBoard[row][col].isClicked()){
            theBoard[row][col].setBackground(myColor);
        }
        else{
            theBoard[row][col].setBackground(blank);
        }
    }
    
    public void enableSend(){
        sendBTN.setEnabled(true);
    }
    
    public void disableSend(){
        sendBTN.setEnabled(false);
    }
    
    public void appendMessage(String message){
        this.setTitle(message);
    }

    public void sendMoveListener(ActionListener listenerForSendBTN){
        sendBTN.addActionListener(listenerForSendBTN);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPANEL;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton sendBTN;
    // End of variables declaration//GEN-END:variables
}
