import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

  
// Server class
class Server {
    static boolean gameSit =false;
    static int turn = 0;
    static int player = 0;
    static Map<Socket, Character> charMap = new HashMap<Socket, Character>();
    static List<Socket> socketList = new ArrayList<>();
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
                if (player < 3) {
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
                    player++;
                }
                if (player == 3) {
                    System.out.println("3 Players has arrived, lets begin!");
                    player++;
                }
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
                String line = null;
                while ((line = in.readLine()) != null) {
                    if(!charMap.containsKey(clientSocket)){
                        String nick = line;
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
                        socketList.add(clientSocket);
                        
                        out.println("Let the game begin!! Please Press Enter!!");
                    }
                    else{
                        if (charMap.size() < 3){
                            out.printf("We wait for %d player.\n", 3-charMap.size());
                        }
                        else if(charMap.size() == 3 && !gameSit){
                            for (Map.Entry<Socket, Character> entry : charMap.entrySet()) {
                                out = new PrintWriter(entry.getKey().getOutputStream(), true);
                                out.println("3 Players has arrived, lets begin!");
                            }
                            gameSit = true;
                        }
                        else{
                            if(turn == 0 && clientSocket.equals(socketList.get(0))){
                                out.printf("Turn: %d - Player: %s \n",turn, charMap.get(clientSocket).getName());
                                turn++;
                            }
                            else if(turn == 1 && clientSocket.equals(socketList.get(1))){
                                out.printf("Turn: %d - Player: %s \n",turn, charMap.get(clientSocket).getName());
                                turn++;
                            }
                            else if(turn == 2 && clientSocket.equals(socketList.get(2))){
                                out.printf("Turn: %d - Player: %s \n",turn, charMap.get(clientSocket).getName());
                                turn++;
                            }
                            else if(turn == 3 && clientSocket.equals(socketList.get(3))){
                                out.printf("Turn: %d - Player: Boss \n",turn);
                                turn = 0;
                            }
                            else{
                                out.printf("it's %s turn to move \n",charMap.get(socketList.get(turn)).getName());
                            }
                        }
                    }
                }
                
            }
            catch (IOException e) {
                if (charMap.containsKey(clientSocket)) {
                    charMap.remove(clientSocket);
                }
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
                    if (charMap.containsKey(clientSocket)) {
                        charMap.remove(clientSocket);
                    }
                    e.printStackTrace();
                }
            }
        }
    }
}