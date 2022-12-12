package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageHandlingServer {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlingServer.class);

    private ServerSocket serverSocket;

    private boolean running = true;

    private int port;

    private List<MessageHandlingClient> connectedClients = new ArrayList<>();

    private List<MessageHandler> messageHandler;

    public MessageHandlingServer(List<MessageHandler> messageHandler, int port) {
        this.messageHandler = messageHandler;
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

                MessageHandlingClient client = new MessageHandlingClient(this, messageHandler, clientSocket);
                connectedClients.add(client);

                LOG.debug("Established connection to client, create input and output streams...");


            } catch (Exception e) {
                LOG.warn("Exception during client connection: ", e);
            }
        }
    }

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

    public static void main(String[] args) {

        List<MessageHandler> handler = new ArrayList<>();
        handler.add(new OutPrintMessageHandler());

        MessageHandlingServer server = new MessageHandlingServer(handler, 4444);
        server.start();
    }


}
