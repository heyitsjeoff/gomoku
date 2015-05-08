/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author PLUCSCE
 */
public class OfflineController {
    
    private offlineDiff theDiffView;
    private JeoffAI theAI;
    private OGameView theView;
    private GameModel theModel;
    
    public static final char ENEMY = '#';
    
    public OfflineController(offlineDiff theDiffView){
        this.theDiffView = theDiffView;
        this.theDiffView.startListener(new StartListener());
    }
    
    class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theModel = new GameModel(30, 30);
            theAI = new JeoffAI(theDiffView.getDiff(), theModel);
            theDiffView.dispose();
            
            theView = new OGameView(theModel);
            theView.moveListener(new MoveListener());
        }
    }
    
    class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] move = theAI.getNextMove().split("\\s+");
            System.out.println("OC: " + move);
            int row = Integer.parseInt(move[0]);
            int col = Integer.parseInt(move[1]);
            theModel.setCell(row, col, ENEMY);
        }
    }
    
}
