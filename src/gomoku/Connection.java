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
    //Variables for Connection
    private String ip = GomokuVariables.initialIP;
    private int port = GomokuVariables.initialPort;
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
    
    //Controllers
    private AuthController theAuthController;
    private CreateAccountController theCreateAccountController;
    private LobbyController theLobbyController;
    private GameHostController theGameHostController;
    private GameClientController theGameClientController;
    
    public Connection(String ip, int port){
        this.ip = ip;
        this.port = port;
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
                        if(messageFromInput.equals(GomokuVariables.logIn)){
                            theAuthController.login();
                            signedIn=true;
                            inLobby=true;
                            write(GomokuVariables.REQUESTLIST);
                        }
                        else if(messageFromInput.equals(GomokuVariables.notLogIn)){
                            theAuthController.notLogin();
                        }
                        else if(messageFromInput.equals(GomokuVariables.uae)){
                            theCreateAccountController.userAlreadyExists();
                        }
                        else if(messageFromInput.equals("user created")){
                            theCreateAccountController.accountCreated();
                        }
                    }
                    else{
                        String[] split = messageFromInput.split("\\s+");
                        String code = split[0];
                        if(code.equals(GomokuVariables.LIST)){
                            StringBuilder sb = new StringBuilder();
                            for(int i=1; i<split.length-1; i++){
                                sb.append(split[i] + " ");
                            }
                            theLobbyController.constructOnlineListDLM(sb.toString());
                        }
                        else if(code.equals(GomokuVariables.INVITEFROM)){
                            String username = split[1];
                            int semi = username.indexOf(";");
                            username = username.substring(0, semi);
                            theLobbyController.addToIncomingList(username);
                        }
                        else if(code.equals(GomokuVariables.ACCEPTFROM)){
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
                        else if(code.equals(GomokuVariables.WITHDRAWFROM)){
                            String userWithdrawingInvite = split[1];
                            int semi = userWithdrawingInvite.indexOf(";");
                            userWithdrawingInvite = userWithdrawingInvite.substring(0, semi);
                            theLobbyController.removeFromIncomingList(userWithdrawingInvite);
                        }
                        else if(code.equals(GomokuVariables.DECLINEFROM)){
                            String decline = split[1];
                            int semi = decline.indexOf(";");
                            decline = decline.substring(0, semi);
                            theLobbyController.removeFromOutgoingList(decline);
                        }
                        else if(code.equals(GomokuVariables.STATSRETURN)){
                            theLobbyController.storeStats(messageFromInput.substring(12, messageFromInput.length()));
                        }        
                    }
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
    
    public void setAuthController(AuthController theAuthController){
        this.theAuthController = theAuthController;
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
