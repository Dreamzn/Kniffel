package net.atos.kniffel.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractServer {


    private static final int MAX_CLIENT = 2;

    private ServerSocket serverSocket;

    private boolean running = true;

    private int port;

    private List<Socket> connectedClients = new ArrayList<>();


    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Created new server socket on port ::= [" + port + "]");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (running) {

            try {
                System.out.println("Wait for client connection...");
                Socket clientSocket = serverSocket.accept();

                connectedClients.removeIf(socket -> socket.isClosed() || !socket.isConnected());

                if (connectedClients.size() < MAX_CLIENT) {
                    connectedClients.add(clientSocket);
                    Thread client = createServerClientThread(clientSocket);
                    client.start();
                    System.out.println("Established connection to client, create input and output streams...");
                } else {
                    System.out.println("Cannot establish connection, limit of " + MAX_CLIENT + " is reached");
                    clientSocket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract Thread createServerClientThread(Socket clientSocket);

    public void stop() throws IOException {
        running = false;
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }
    }


}
