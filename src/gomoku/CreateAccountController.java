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
    private static final String uaeL = "User already exists";
    private static final String pdnm = "Passwords do not match";
    private static final String created = "user created";
    private static final String ucs = "Username can not contain spaces";
    
    public CreateAccountController(CreateAccountView theView, Connection theConnection, MainView theMainView){
        this.theView = theView;
        this.theMainView = theMainView;
        this.theConnection = theConnection;
        this.theView.createListener(new CreateListener());
        this.theView.backListener(new backListener());
        theView.enterListener(new EnterListener());
    }
    
    public void createAccount(){
        if(!theView.getPassword1().equals(theView.getPassword2())){
                theView.message(pdnm);
        }
        else if(theView.getUsername().trim().contains(" ")){
            theView.message(ucs);
        }
        else{
            String message = "!" + theView.getUsername()+ " " + theView.getPassword1();
            this.theConnection.write(message);
        }
    }

    public void userAlreadyExists() {
        theView.message(uaeL);
    }

    public void accountCreated() {
        String username = theView.getUsername();
        String password = theView.getPassword1();
        theConnection.write(username + " "+ password);
        theView.dispose();
    }
    

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

    class backListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.dispose();
            theMainView.setVisible(true);
        }
    }

    class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccount();
        }//action
    }//CreateListener

    
    
}
