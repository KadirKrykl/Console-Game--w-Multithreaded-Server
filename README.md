# Multiplayer FRP using Multithreaded Server

MP FRP game with Multithreaded Server via Java Net library.

## Workflow

 1. You have to clone this repository via "`git clone https://github.com/KadirKrykl/Console-Game--w-Multithreaded-Server.git`"
 2. All java classes must be compiled. You can run "`javac Server.java`" and "`javac Client.java`"
 3. Server up via "`java Server`". This code use port number "1234". If you want another number, you can change the line 27 in "Server.java"  -> `server =  new  ServerSocket(1234);`
 4. Clients up via "`java Client`"
 5. After the Client up, client ask you for Username and Class for your character. 
 6. The game will not start until 3 players(clients) connected to server.
 7. When 3 player are connected to server, the game start and another player can not join.
