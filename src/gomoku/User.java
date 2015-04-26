package gomoku;

/**
 *
 * @author heyitsjeoff
 */
public class User {
    
    private String username, password;
    private int key;
    private String theCode = "ultron";
    //private int wins, losses, ties, games;
    
    /**
     * creates a user with username and encrypted password
     * @param username username of user
     * @param password will be encrypted and set as password
     */
    public User(String username, String password){
        this.username = username;
        createKey(theCode);
        this.password = encrypt(password);
        //this.wins = 0; this.losses = 0; this.ties = 0; this.games = 0;
    }
    
    /**
     * creates a user with username and already encryped password
     * used for importing from text file
     * @param username
     * @param password
     * @param whoCares
     */
    public User(String username, String password, String whoCares){
        this.username = username;
        this.password = password;
    }

    /**
     * returns the username of User
     * @return username of User
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * returns the encrypted password of User
     * @return password of User
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * generates a key to be used for encryption
     * @param code string used to generate key
     */
    public void createKey(String code){
        int sum = 0;
        for(int i=0; i<code.length(); i++){
            sum = sum + code.charAt(i);
        }
        key = sum/code.length();
        if(key==94){
            key = 42;
        }
    }
    
    /**
     * encrypts a string
     * @param a string to be encrypted
     * @return encrypted version of a
     */
    public String encrypt(String a){
        int sum = 0;
        for(int i=0; i<a.length(); i++){
            sum = sum + a.charAt(i);
        }
        int key = sum/a.length();
        if(key==94){
            key = 42;
        }
        String newString = "";
        for (int i = 0; i < a.length(); i++){
            int shanku = a.charAt(i);
            shanku += key;
            while (shanku > 126){
                shanku -= 94;
            }
            newString = newString + (char)shanku;
        }
        return newString;
    }//encrypt
    
    /**
     * decrypts a string
     * @param a string to be decrypted
     * @return decrypted a
     */
    public String decrypt(String a){
        String newString = "";
        for (int i = 0; i < a.length(); i++) {
            int shanku = a.charAt(i);
            shanku -= key;
            while (shanku < 32) {
                shanku += 94;
            }
            newString = newString + (char)shanku;
        }
    return newString;
    }
    
    /**
     * converts User object to a string
     * @return string representation of User
     */
    public String toString(){
        String a = this.getUsername() + " " + this.getPassword();
        return a;
    }
}
