package gomoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerModel {
    
    private ArrayList<User> userList;
    private ArrayList<Client> onlineUsers;
    private ArrayList<Score> theScore;
    private String fileName = "data";
            
    /**
     * creates a servermodel and imports stored users from data.txt
     */
    public ServerModel(){
        this.userList = new ArrayList<User>();
        this.onlineUsers = new ArrayList<Client>();
        readFile(fileName);
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
    
    public void updateAllOnlineList(String message){
        for(int i=0; i<this.onlineUsers.size(); i++){
            onlineUsers.get(i).sendMessage(message);
        }
    }
    
    /**
     * gets the list of online users
     * @return the list of online users
     */
    public ArrayList<Client> getOnlineList(){
        return onlineUsers;
    }
    
    public String getOnlineUsernames(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<this.onlineUsers.size(); i++){
            sb.append(this.onlineUsers.get(i).getUsername() + " ");
        }
        return sb.toString();
    }

    
    /**
     * determines if the user with a given username and password is valid
     * @param check user object that should be in userList
     * @return true if username and password match
     */
    public boolean authenticate(User check){
        String theUsername = check.getUsername();
        String thePassword = check.getPassword();
        for(int i=0; i<userList.size(); i++){
            if(userList.get(i).getUsername().equals(theUsername)){
                if(userList.get(i).getPassword().equals(thePassword)){
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
    public boolean userExists(String username){
        for(int i=0; i<userList.size(); i++){
            if(userList.get(i).getUsername().equalsIgnoreCase(username)){
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
        System.out.println(onlineUsers.toString());
    }
    
    /**
     * adds a new user to the list and writes to data.txt
     * @param newUser user being added
     */
    public void addUser(User newUser){
        userList.add(newUser);
        //terrible way to do this
        writeFile(fileName);
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
		for(int i=0; i<userList.size(); i++){
                    outfile.println(userList.get(i).toString());
                }
		outfile.close();
            } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
	}//writeFile
    public boolean userExists(User check){
        for(int i=0; i<userList.size(); i++){
            if(userList.get(i).getUsername().equalsIgnoreCase(check.getUsername())){
                return true;
            }//if
        }//for
        return false;
    }//userExists
    
    public void readScoreFile(String filename){
    Scanner infile = null;
	try{
            infile = new Scanner(new File(filename + ".txt"));
	} catch (FileNotFoundException e){
            System.out.println("File not found " + filename);
            e.printStackTrace();
	}
	while(infile.hasNext()){
            String scoreLine = infile.nextLine();
            String[] split = scoreLine.split("\\s+");
            Score newScore = new Score(split[1] + " " + split[2] + " " + split[3]);
            newScore.setUsername(split[0]);
            this.theScore.add(newScore);
        }//while
    }//getFile
    
    public void writeScoreFile(String filename){
        try {
        	PrintWriter outfile = new PrintWriter(filename + ".txt");
		for(int i=0; i<theScore.size(); i++){
                    outfile.println(theScore.get(i).toString());
                }
		outfile.close();
            } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
	}//writeFile
	
	/**
	 * Processes an invitation. Used when usernameFrom invites usernameTo to a game.
	 * @param usernameFrom The username of the inviting user
	 * @param usernameTo The username of the invited user
	 * @return true if invitation is sent, false if invitation not sent because
	 *			usernameTo is offline or does not exist
	 */
	public boolean invite(String usernameFrom, String usernameTo){
		Client recipient = findOnlineUser(usernameTo);
		if(recipient != null){
			recipient.invite(usernameFrom);
			return true;
		} else return false;
	}
	
	/**
	 * Processes an acceptance. Used when usernameTo has previously invited
	 *		usernameFrom to a game, and usernameFrom accepts the invitation.
	 * @param usernameFrom The username of the inviting user
	 * @param usernameTo The username of the invited user
	 * @param IP the IP address and port of usernameFrom. Required so that a
	 *		connection can be established directly between the two users.
	 * @return true if invitation is sent, false if invitation not sent because
	 *			usernameTo is offline or does not exist
	 */
	public boolean accept(String usernameFrom, String usernameTo, String IP){
		Client recipient = findOnlineUser(usernameFrom);
		if(recipient != null){
			recipient.accept(usernameTo, IP);
			return true;
		} else return false;
	}
	
	/**
	 * Processes a withdrawn invitation. Used when usernameFrom had previously 
	 *		invited usernameTo to a game, but wishes to withdraw that invitation.
	 * 
	 * @param usernameFrom The username of the inviting user
	 * @param usernameTo The username of the invited user
	 * @return true if invitation is sent, false if withdrawl not sent because
	 *		usernameTo is offline or does not exist
	 */
	public boolean withdraw(String usernameFrom, String usernameTo){
		Client recipient = findOnlineUser(usernameTo);
		if(recipient != null){
			recipient.withdraw(usernameFrom);
			return true;
		} else return false;
	}
	
	/**
	 * Processes a declined invitation. Used when usernameFrom declines
	 *		an invitation from usernameTo.
	 * 
	 * @param usernameFrom The username of the declining user
	 * @param usernameTo The username of the declined user
	 * @return true if invitation is sent, false if invitation not sent because
	 *		usernameTo is offline or does not exist
	 */
	public boolean decline(String usernameFrom, String usernameTo){
		Client recipient = findOnlineUser(usernameTo);
		if(recipient != null){
			recipient.decline(usernameFrom);
			return true;
		} else return false;
	}
	
	/**
	 * Searches for the Client object for an online user
	 * 
	 * @param username the name of the user being searched for
	 * @return the Client object associated with the user in question, or null
	 *			if the user in question is not online or does not exist.
	 */
	private Client findOnlineUser(String username){
		for(Client c : onlineUsers){
			if(c.compareTo(username) == 0)
				return c;
		}
		return null;
	}
}//class
