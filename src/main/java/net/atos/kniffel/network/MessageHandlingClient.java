package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageHandlingClient extends Thread {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlingClient.class);

    /**
     * Counter to create unique client IDs
     */
    private static AtomicInteger ID = new AtomicInteger(0);

    /**
     * The TCP/IP socket
     */
    private Socket socket;

    /**
     * Stream to receive messages from the client.
     */
    private BufferedReader inputStream;

    /**
     * Stream to send messages to the client.
     */
    private BufferedWriter outputStream;

    /**
     * List of message handler.
     */
    private List<MessageHandler> messageHandler;

    /**
     * The server.
     */
    private MessageHandlingServer server;


    /**
     * Create a new client for message handling.
     */
    public MessageHandlingClient(MessageHandlingServer server, List<MessageHandler> messageHandler, Socket clientSocket) throws IOException {
        setName("CLIENT-" + ID.incrementAndGet());

        this.server = server;
        this.messageHandler = messageHandler;

        this.socket = clientSocket;
        this.inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.outputStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        this.start();
    }

    @Override
    public void run() {


        while (true) {

            try {

                LOG.debug("Wait for message...");
                String message = inputStream.readLine();
                messageHandler.forEach(handler -> handler.handleMessage(server, this, message));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String toString() {
        return this.getName();
    }
}
