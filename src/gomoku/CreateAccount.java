/*
Jeoff Villanueva
Project
CSCE 320 Spring
Date
Java used in Netbeans
Sources:

 */
package gomoku;


public class CreateAccount {
    
    private User newUser;
    private ServerModel theModel;
    private CreateAccountView theView;
    
    public CreateAccount(CreateAccountView theView){
        this.theModel = theModel;
        this.theView = theView;
    }
    
    public void createUser(String username, String password){
        this.newUser = new User(username, password);
        if(!checkUsers(this.newUser)){
            addUser(newUser);
        }
    }
    
    public boolean checkUsers(User check){
        return theModel.userExists(check);
    }
    
    public void addUser(User newUser){
        theModel.addUser(newUser);
    }

}
