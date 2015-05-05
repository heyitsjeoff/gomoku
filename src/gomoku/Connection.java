package gomoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connection implements Runnable{
    
    private String ip = "127.0.0.1";
    private int port = 8080; //port of ip
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader br;
    private Thread worker;
    private byte[] byteArray = new byte[2000];
    private boolean streamsConnected = false;
    private boolean signedIn = false;
    private boolean inLobby = false;
    private boolean inGame = false;
    private String username;
    
    private static final String logIn = "y";
    private static final String notLogIn = "n";
    private static final String uae = "user already exists";
    private static final String created = "user created";
    private static final String LIST = "LIST";
    private static final String INVITETO = "INVITETO";
    private static final String INVITEFROM = "INVITEFROM";
    private static final String REQUESTLIST = "REQUESTLIST;";
    private static final String ACCEPTFROM = "ACCEPTFROM";
    private static final String WITHDRAWFROM = "WITHDRAWFROM";
    private static final String DECLINETO = "DECLINETO";
    private static final String DECLINEFROM = "DECLINEFROM";
        public static final String nm = "NEXTMOVE";
    
    private AuthController aController;
    private CreateAccountController theCreateAccountController;
    private LobbyController theLobbyController;
    private GameHostController theGameController;
    private GameClientController theGameClientController;
    
    public Connection(String ip){
        this.ip = ip;
    }

    @Override
    public void run() {
        boolean connected = true;
        while(connected){
            try {
                int count = this.inputStream.read(byteArray);
                if(count>0){
                    String messageFromInput = new String(byteArray, 0, count);
                    if(!inLobby){
                        if(messageFromInput.equals(logIn)){
                            aController.login();
                            signedIn=true;
                            inLobby=true;
                            write(REQUESTLIST);
                        }
                        else if(messageFromInput.equals(notLogIn)){
                            aController.notLogin();
                        }
                        else if(messageFromInput.equals(uae)){
                            theCreateAccountController.userAlreadyExists();
                        }
                        else if(messageFromInput.equals("user created")){
                            theCreateAccountController.accountCreated();
                        }
                    }
                    else if(!inGame){
                        String[] split = messageFromInput.split("\\s+");
                        String code = split[0];
                        if(code.equals(LIST)){
                            StringBuilder sb = new StringBuilder();
                            for(int i=1; i<split.length-1; i++){
                                sb.append(split[i] + " ");
                            }
                            theLobbyController.updateOnlineList(sb.toString());
                        }
                        else if(code.equals(INVITEFROM)){
                            String username = split[1];
                            int semi = username.indexOf(";");
                            username = username.substring(0, semi);
                            theLobbyController.addToIncomingList(username);
                        }
                        else if(code.equals(ACCEPTFROM)){
                            String userAcceptingInvite = split[1];
                            String ipOfAcceptingUser = split[2];
                            int semi = ipOfAcceptingUser.indexOf(";");
                            ipOfAcceptingUser = ipOfAcceptingUser.substring(0, semi);
                            int colon = ipOfAcceptingUser.indexOf(":");
                            ipOfAcceptingUser = ipOfAcceptingUser.substring(0, colon);
                            theLobbyController.connectToHost(ipOfAcceptingUser);
                            this.inGame=true;
                            this.theGameClientController = theLobbyController.getClientController();
                        }
                        else if(code.equals(WITHDRAWFROM)){
                            String userWithdrawingInvite = split[1];
                            int semi = userWithdrawingInvite.indexOf(";");
                            userWithdrawingInvite = userWithdrawingInvite.substring(0, semi);
                            theLobbyController.removeFromIncomingList(userWithdrawingInvite);
                        }
                        else if(code.equals(DECLINEFROM)){
                            String decline = split[1];
                            int semi = decline.indexOf(";");
                            decline = decline.substring(0, semi);
                            theLobbyController.removeFromOutgoingList(decline);
                        }
                    }
                    else{
                        String[] split = messageFromInput.split("\\s+");
                        String code = split[0];
                        if(code.equals(nm)){
                            String move = split[1] + " " + split[2] + " " + split[3];               
                            this.theGameClientController.updateBoard(move);
                        }
                    }//in game
                }//connected
                else {
                    connected = false;
                }//disconnected
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setAuthController(AuthController aController){
        this.aController = aController;
    }
    
    public void setCreateAccountController(CreateAccountController theCreateAccountController){
        this.theCreateAccountController = theCreateAccountController;
    }
    
    public void setLobbyController(LobbyController theLobbyController){
        this.theLobbyController= theLobbyController;
    }
    
    public void write(String message){
        byte[] buffOut;
        try {
            buffOut = message.getBytes();
            this.outputStream.write(buffOut, 0, message.length());
            this.outputStream.flush();
            System.out.println(message + " has been flushed");
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startThread(){
        this.worker = new Thread(this);
        worker.start();
    }
    
    public void connect(){
        try {
            this.socket = new Socket(InetAddress.getByName(ip), port);
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.inputStream));
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setIP(String number){
        this.ip = number;
    }
    
    public void setPort(int number){
        this.port = number;
    }
    
}
