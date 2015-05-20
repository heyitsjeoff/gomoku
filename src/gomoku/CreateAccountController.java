package gomoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreateAccountController {
    
    private User newUser;
    private CreateAccountView theView;
    private MainView theMainView;
    private Connection theConnection;
    
    /**
     *
     * @param theView the view for the create account window
     * @param theConnection connection to the server
     * @param theMainView the view for the main menu
     */
    public CreateAccountController(CreateAccountView theView, Connection theConnection, MainView theMainView){
        this.theView = theView;
        this.theMainView = theMainView;
        this.theConnection = theConnection;
        this.theView.createListener(new CreateListener());
        this.theView.backListener(new backListener());
        theView.enterListener(new EnterListener());
    }
    
    /**
     * attempts to create an account
     */
    public void createAccount(){
        if(theView.getUsername().contains(" ")){
            theView.message(GomokuVariables.ucs);
        }
        else if(!theView.getPassword1().equals(theView.getPassword2())){
                theView.message(GomokuVariables.pdnm);
        }
        else if(theView.getPassword1().contains(" ")){
            theView.message(GomokuVariables.pcs);
        }
        else if(theView.getPassword1().length()<6){
            theView.message(GomokuVariables.pts);
        }
        else{
            String message = "!" + theView.getUsername()+ " " + theView.getPassword1();
            this.theConnection.write(message);
        }
    }
    
    /**
     * creats an anon user
     * @param anon
     */
    public void createAnon(String anon){
        String message = "!" + anon + " " + anon;
        theView.setUsernamePassword(anon);
        this.theConnection.write(message);
    }

    /**
     * updates the view saying the user already exists
     */
    public void userAlreadyExists() {
        theView.message(GomokuVariables.uaeL);
    }

    /**
     * logs the user in with the newly created credentials and dispose the view
     */
    public void accountCreated() {
        String username = theView.getUsername();
        String password = theView.getPassword1();
        theConnection.write(username + " "+ password);
        theView.dispose();
    }
    
    /**
     * enter key listener
     */
    class EnterListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            //NA
        }//keyTyped
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                createAccount();
            }//keyPressed
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //NA
        }//keyReleased  
    }

    /**
     * back button listener
     */
    class backListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.dispose();
            theMainView.setVisible(true);
        }
    }

    /**
     * create account listener
     */
    class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccount();
        }//action
    }//CreateListener
  
}
