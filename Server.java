import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

  
// Server class
class Server {
    static boolean gameSit =false;
    static int turn = 0;
    static int player = 0;
    static Map<Socket, Character> charMap = new HashMap<Socket, Character>();
    static Map<Socket, String> msgMap = new HashMap<Socket, String>();
    static List<Socket> socketList = new ArrayList<>();
    static List<Boss> bosses = new ArrayList<>();
    static String[] skills = {"1", "2"};
    static String msg = "";
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
                while ((line = in.readLine()) != null){
                    if(!charMap.containsKey(clientSocket)){
                        String nick = line;
                        out.println("Choose a class : 1:Warrior  2:Mage  3:Archer");
                        String hero_class = in.readLine();
                        if(hero_class.equals("1")){
                            Character warrior = new Warrior(nick);
                            charMap.put(clientSocket, warrior);
                            socketList.add(clientSocket);
                            msgMap.put(clientSocket, "");
                            out.println("Press Enter");
                        }else if(hero_class.equals("2")) {
                            Character mage = new Mage(nick);
                            charMap.put(clientSocket, mage);
                            socketList.add(clientSocket);
                            msgMap.put(clientSocket, "");
                            out.println("Press Enter");
                        }else if(hero_class.equals("3")){
                            Character archer = new Archer(nick);
                            charMap.put(clientSocket, archer);
                            msgMap.put(clientSocket, "");
                            socketList.add(clientSocket);
                            out.println("Press Enter");
                        }
                    }
                    else{
                        if (charMap.size() < 3){
                            msg = String.format("We wait for %d player.", 3-charMap.size());
                            msgMap.replace(clientSocket, msg);
                        }
                        else if(charMap.size() == 3 && !gameSit){
                            msgMap.forEach((socketNumber, message) -> {
                                msgMap.replace(socketNumber, "3 Players has arrived, lets begin!");
                            });
                            bosses.add(new Boss("Queen of Pain"));
                            bosses.add(new Boss("Rosh"));
                            gameSit = true;
                        }
                        else{
                            String playerName = charMap.get(socketList.get(turn)).getPName();
                            if(clientSocket.equals(socketList.get(turn))){
                                String[] acts = charMap.get(clientSocket).act_list();
                                if (line.equals("1") || line.equals("2")) {
                                    int[] response = charMap.get(clientSocket).attack(Integer.parseInt(line));
                                    bosses.get(0).sethp(bosses.get(0).gethp() - response[1]);
                                    msgMap.forEach((socketNumber, message) -> {
                                        msg = String.format("Player %s attacted to boss with %s and rolled %d. So hit %d. Boss has %d hp",playerName,acts[response[2]],response[0],response[1],bosses.get(0).gethp());
                                        msgMap.replace(socketNumber, msg);
                                    });
                                    if(bosses.get(0).gethp() <= 0){
                                        if(bosses.size() == 2){
                                            msgMap.forEach((socketNumber, message) -> {
                                                msg = String.format("Boss %s died. New Boss is %s", bosses.get(0).getName(), bosses.get(1).getName());
                                                msgMap.replace(socketNumber, msg);
                                            });
                                        }
                                        else{
                                            msgMap.forEach((socketNumber, message) -> {
                                                msg = String.format("Boss %s died. Congratulations Champions You Win", bosses.get(0).getName(), bosses.get(1).getName());
                                                msgMap.replace(socketNumber, msg);
                                            });
                                        }
                                        bosses.remove(0);
                                    }
                                    turn++;
                                }
                                else{
                                    msg = String.format("Now Your Turn. Choose a skill; 1:%s  2:%s", acts[0],acts[1]);
                                    msgMap.replace(clientSocket, msg);
                                }
                                if (turn >= socketList.size() && bosses.size() > 0) {
                                    Random rand = new Random();
                                    int idx = rand.nextInt(socketList.size());
                                    int[] response = bosses.get(0).attack();
                                    String target = charMap.get(socketList.get(idx)).getPName();
                                    int tempHp = charMap.get(socketList.get(idx)).gethp();
                                    charMap.get( socketList.get(idx) ).sethp( tempHp - response[1] );
                                    if (tempHp - response[1] <= 0) {
                                        socketList.remove(idx);
                                        msgMap.forEach((socketNumber, message) -> {
                                            msg = String.format("Boss attacted to %s with rolled %d. So hit %d. Unfourtunetly %s died.",target, response[0], response[1], target);
                                            msgMap.replace(socketNumber, msg);
                                        });
                                    }
                                    else{
                                        msgMap.forEach((socketNumber, message) -> {
                                            msg = String.format("Boss attacted to %s with rolled %d. So hit %d. Player %s has %d hp.",target, response[0], response[1], target, (tempHp-response[1]));
                                            msgMap.replace(socketNumber, msg);
                                        });
                                    }
                                    turn = 0;
                                }
                            }
                            else{
                                msg = String.format("it's %s turn to move",playerName);
                                msgMap.replace(clientSocket, msg);
                            }
                        }
                        if (!msgMap.get(clientSocket).equals("")) {
                            out.println(msgMap.get(clientSocket));
                            msgMap.replace(clientSocket, "");
                        }
                        else{
                            out.println("\n");
                            msgMap.replace(clientSocket, "");
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