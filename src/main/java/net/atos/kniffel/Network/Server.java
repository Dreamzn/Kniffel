package net.atos.kniffel.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private ServerSocket serverSocket;
    private List<ServerClientHandler> connectedClients = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Created new server socket on port ::= [" + port + "], wait for connection");

            while (true) {
                System.out.println("Wait for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Established connection to client, create input and output streams...");
                ServerClientHandler client = new ServerClientHandler("Client_" + counter.incrementAndGet(), clientSocket);
                client.start();
                for(ServerClientHandler clientInList : connectedClients) {
                    clientInList.addPeerClientHandler(client);
                    client.addPeerClientHandler(clientInList);
                }
                connectedClients.add(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(4444);
    }
}
