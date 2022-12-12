package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServer {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractServer.class);

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
            LOG.info("Created new server socket on port ::= [{}]", port);
        } catch (Exception e) {
            LOG.warn("Exception during server socket creation: ", e);
            System.exit(-1);
        }

        while (running) {

            try {
                LOG.debug("Wait for client connection...");
                Socket clientSocket = serverSocket.accept();

                connectedClients.removeIf(socket -> socket.isClosed() || !socket.isConnected());

                if (connectedClients.size() < MAX_CLIENT) {
                    connectedClients.add(clientSocket);
                    Thread client = createServerClientThread(clientSocket);
                    client.start();
                    LOG.debug("Established connection to client, create input and output streams...");
                } else {
                    LOG.debug("Cannot establish connection, limit of {} is reached", MAX_CLIENT);
                    clientSocket.close();
                }

            } catch (Exception e) {
                LOG.warn("Exception during client connection: ", e);
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
