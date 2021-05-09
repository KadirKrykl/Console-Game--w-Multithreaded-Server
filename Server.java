import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import game.*;

  
// Server class
class Server {
    
    static Map<Socket, Character> charMap = new HashMap<Socket, Character>();
    public static void main(String[] args)
    {
        ServerSocket server = null;
  
        try {
  
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
  
            // running infinite loop for getting
            // client request
            while (true) {
  
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
  
                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                                   + client.getInetAddress()
                                         .getHostAddress());
  
                
                // create a new thread object
                ClientHandler clientSock
                    = new ClientHandler(client);
                
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
  
    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        
        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            
            try {
                // get the outputstream of client
                out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
  
                // get the inputstream of client
                in = new BufferedReader(
                    new InputStreamReader(
                        clientSocket.getInputStream()));

                if(!charMap.containsKey(clientSocket)){
                    // get the outputstream of client
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    // get the inputstream of client
                    in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream()));
                    //charMap.put("a", new Integer(100));
                    //out.println("Enter nickname:");
                    String nick = in.readLine();
                    boolean class_check = false;
                    while (!class_check) {
                        out.println("Choose a class : 1:Warrior  2:Mage  3:Archer");
                        String hero_class = in.readLine();
                        if(hero_class.equals("1")){
                            Character warrior = new Warrior(nick);
                            charMap.put(clientSocket, warrior);
                            class_check = true;
                        }else if(hero_class.equals("2")) {
                            Character mage = new Mage(nick);
                            charMap.put(clientSocket, mage);
                            class_check = true;
                        }else if(hero_class.equals("3")){
                            Character archer = new Archer(nick);
                            charMap.put(clientSocket, archer);
                            class_check = true;
                        }
                    }
                    out.println("Let the game begin!!");
                }
                else{
                    String line;
                    while ((line = in.readLine()) != null) {
                        // line = message from client 
                        out.println(line); //Clients read from here
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}