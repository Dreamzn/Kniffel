package net.atos.kniffel.network;

import net.atos.kniffel.network.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Messager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageHandlingServer{

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlingServer.class);
    /**
     * The TCP/IP socket
     */
    private ServerSocket serverSocket;

    /**
     * Boolean for while loop
     */
    private boolean running = true;

    /**
     * Port number
     */
    private int port;
    /**
     * List of connected clients
     */
    private List<MessageHandlingClient> connectedClients = new ArrayList<>();
    /**
     * List of messageHandler
     */
    private List<MessageHandler> messageHandler;

    /**
     * Create a new Server
     */
    public MessageHandlingServer(List<MessageHandler> messageHandler, int port) {
        this.messageHandler = messageHandler;
        this.port = port;
    }

    /**
     * Start server thread to connect with clients
     */
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

                MessageHandlingClient client = new MessageHandlingClient(this, messageHandler, clientSocket);
                connectedClients.add(client);

                LOG.debug("Established connection to client, create input and output streams...");


            } catch (Exception e) {
                LOG.warn("Exception during client connection: ", e);
            }
        }
    }

    /**
     * Closes the server socket
     * @throws IOException
     */
    public void stop() throws IOException {
        running = false;
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    @Override
    public String toString() {
        return "MessageHandlingServer{" +
                "serverSocket=" + serverSocket +
                ", running=" + running +
                ", port=" + port +
                ", connectedClients=" + connectedClients +
                ", messageHandler=" + messageHandler +
                '}';
    }

    /**
     * Get the liest of messageHandler
     * @return messageHandler list
     */
    public List<MessageHandler> getMessageHandler() {
        return messageHandler;
    }

    /**
     * Get the list with connected clients
     * @return list with connected clients
     */
    public List<MessageHandlingClient> getConnectedClients() {
        return connectedClients;
    }

    public static void main(String[] args) {

        List<MessageHandler> handler = new ArrayList<>();
        handler.add(new MessageAll());
        handler.add(new MessageRegister());
        handler.add(new MessageExit());
        handler.add(new MessagePrivate());

        MessageHandlingServer server = new MessageHandlingServer(handler, 4444);
        server.start();
    }

}
