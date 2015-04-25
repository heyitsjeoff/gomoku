package gomoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author heyitsjeoff
 */
public class ServerModel {
    
    private ArrayList<User> list;
    private ArrayList<Client> onlineUsers;
            
    /**
     * creates a servermodel and imports stored users from data.txt
     */
    public ServerModel(){
        this.list = new ArrayList<User>();
        this.onlineUsers = new ArrayList<Client>();
        readFile("data");
    }
    
    /**
     * gets username of User object
     * @param theUser user in question
     * @return user's username
     */
    public String getUsername(User theUser){
        return theUser.getUsername();
    }
    
    /**
     * gets password of User object
     * @param theUser user in question
     * @return user's password
     */
    public String getPassword(User theUser){
        return theUser.getPassword();
    }
    
    /**
     * gets the list of online users
     * @return the list of online users
     */
    public ArrayList<Client> getOnlineList(){
        return onlineUsers;
    }
    
    /**
     * determines if the user with a given username and password is valid
     * @param check user object that should be in list
     * @return true if username and password match
     */
    public boolean authenticate(User check){
        String theUsername = check.getUsername();
        String thePassword = check.getPassword();
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getUsername().equals(theUsername)){
                if(list.get(i).getPassword().equals(thePassword)){
                    return true;
                }
            }
        }
        return false;
    }//authenticate

    /**
     * checks if a user with a specific username exists
     * @param check user in question
     * @return true if username is already used
     */
    public boolean userExists(User check){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getUsername().equalsIgnoreCase(check.getUsername())){
                return true;
            }//if
        }//for
        return false;
    }//userExists

    /**
     * adds a user to list of online users
     * @param newOnlineUser user being added
     */
    public void addOnline(Client newOnlineUser){
        this.onlineUsers.add(newOnlineUser);
        //method asking ServerController to update all client connection lists
    }
    
    /**
     * removes a user from the list of online users
     * @param oldOnlineUser user being removed
     */
    public void removeOnline(Client oldOnlineUser){
        this.onlineUsers.remove(oldOnlineUser);
    }
    
    /**
     * adds a new user to the list and writes to data.txt
     * @param newUser user being added
     */
    public void addUser(User newUser){
        list.add(newUser);
        //terrible way to do this
        writeFile("data");
    }//addUser
    
    

    //file handling-----------
    /**
    * Reads in a file, and imports the users into a list
    * @param filename the name of the file that is being used to import data
    */
    public void readFile(String filename){
    Scanner infile = null;
	try{
            infile = new Scanner(new File(filename + ".txt"));
	} catch (FileNotFoundException e){
            System.out.println("File not found " + filename);
            e.printStackTrace();
	}
	while(infile.hasNext()){
            String userAndPass = infile.nextLine();
            String[] split = userAndPass.split("\\s+");
            String username = split[0];
            String password = split[1];
            User rUser = new User(username, password, "whocares");
            addUser(rUser);
        }//while
    }//getFile

    /**
     * writes to a file the username and password of users in list
     * @param filename the name of the file that is being created
     */
    public void writeFile(String filename){
        try {
        	PrintWriter outfile = new PrintWriter(filename + ".txt");
		for(int i=0; i<list.size(); i++){
                    outfile.println(list.get(i).toString());
                }
		outfile.close();
            } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
	}//writeFile
}//class
