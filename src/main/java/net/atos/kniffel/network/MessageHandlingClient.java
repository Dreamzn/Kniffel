package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageHandlingClient extends Thread implements OutPrintInAndOutput{

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
     * Name of the participant
     */
    private String name;


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

    /**
     * Set the name of the participant
     * @param name of participant
     */
    public void setParticipant(String name) {
        this.name = name;
    }

    /**
     * Get the name of the participant
     * @return participant name
     */
    public String getParticipant() {
        return name;
    }

    /**
     * Starting MessageHandlingClient thread for receiving messages
     */
    @Override
    public void run() {

        boolean run = true;

        while (run) {

            try {
                LOG.debug("Wait for message...");
                String message = inputStream.readLine();

                if (message == null) {
                    LOG.debug("Lost connection to client, close socket...");
                    socket.close();
                    break;
                }
                Message msgObj = Message.fromJSON(message);
                LOG.info("Received message: " + message);
                messageHandler.forEach(handler -> handler.handleMessage(server, this, msgObj));

            } catch (IOException e) {
                LOG.warn("Error during message from client: ", e);

            }
        }

    }

    /**
     * Close the socket
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        socket.close();
    }

    /**
     * Send the message
     * @param message
     */
    public void sendMessage(Message message){
        try {
            outputStream.write(message.toJSON());
            outputStream.newLine();
            outputStream.flush();
        } catch (IOException e) {
            LOG.warn("Exception for sending a message: ", e);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public void Printer(Message message) {
        System.out.println("Received message: " + message);
    }
}
