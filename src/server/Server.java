
package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import database.dao.UserDao;
import database.dao.UserDaoImpl;
import database.entity.User;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;


/**
 *
 * @author Muhammad
 */
public class Server {
    ServerSocket myServerSocket;
    public Server(){
        try {
            //suit my server to recieve clients
            myServerSocket = new ServerSocket(6000);
            while(true){
                //welcoming new client
                Socket s = myServerSocket.accept();
                //handle my client
                new ClientHandler(s);
            }
        } catch (IOException ex) {
                ex.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new Server();
    }
    
}

class ClientHandler extends Thread{
    private DataInputStream dis;
    private PrintStream ps;
    private Gson gson;
    private User user;
    private UserDao userDao;
    private String reply;
    private JSONObject obj;
          
    static Vector<ClientHandler> clients= new Vector<ClientHandler>();
    public ClientHandler(Socket s){
        gson = new Gson();
        
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            //ps.println("HI FROM SERVER");
            ClientHandler.clients.add(this);
            System.out.println("number of clients : " + clients.size());
            System.out.println("____________________________________________________________");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            try {
                String jsonStr = dis.readLine();
                //System.out.println(jsonStr);
                //GSON
                JsonObject jsonObject = gson.fromJson(jsonStr, JsonObject.class);
                String request = jsonObject.get("request").getAsString();
                switch(request){
                    case"register":
                        System.out.println("Register case");
                        user = gson.fromJson(jsonStr, User.class);
                        //System.out.println(user.getUsername());
                        //System.out.println(user.getPassword());
                        userDao = new UserDaoImpl();
                        boolean added = userDao.regist(user);
                        System.out.println("added : " + added);
                        break;

                    case"login":
                        System.out.println("Login case");
                        user = gson.fromJson(jsonStr, User.class);
                        //System.out.println(user.getUsername());
                        //System.out.println(user.getPassword());
                        userDao = new UserDaoImpl();
                        boolean login = userDao.login(user);
                        System.out.println("login : " + login);
                        if(login){
                            reply = "Login Successfully";
                        }
                        else{
                            reply = "Login Failed";
                        }
                        sendToClient(reply);
                        break;

                    case"message":
                        System.out.println("Message case");
                        String username = jsonObject.get("username").getAsString();
                        String msg = jsonObject.get("msg").getAsString();
                        //System.out.println(username + " : " + msg);
                        obj = new JSONObject();
                        obj.put("reply", "message");
                        obj.put("username", username);
                        obj.put("msg", msg);
                        String jString = obj.toJSONString();
                        sendMsg(jString);




                }
            } catch (SocketException se){
                try {
                    dis.close();
                    clients.remove(this);

                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                se.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            } 
        }
    }
    
    public void sendToClient(String reply){
        for(ClientHandler client : clients){
            if(client.hashCode() == this.hashCode()){
                client.ps.println(reply);
            }
        }
    }
    
    public void sendMsg(String msg){
        for(ClientHandler client : clients){
            client.ps.println(msg);
        }
    }
            
}
