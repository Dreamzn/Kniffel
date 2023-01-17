package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * A Client to connect with a server to communicate with it or other connected
 */
public class Client extends Thread implements OutPrintInAndOutput {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);
    /**
     * Client ID
     */
    private String ip;
    /**
     * Port number
     */
    private int port;
    /**
     * The TCP/IP socket
     */
    private Socket clientSocket;
    /**
     * Stream to send messages to the client.
     */
    private PrintWriter socketOutputStrm;
    /**
     * Stream to receive messages from the client.
     */
    private BufferedReader socketInputStrm;

    private boolean loopExit = true;

    /**
     * Create a new instance of a client with an ID and port to connect with. Input and output socket, reader and writer and  it's name  were created automatic
     *
     * @param ip
     * @param port
     * @throws IOException
     */
    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        clientSocket = new Socket(ip, port);
        clientSocket.setSoTimeout(0);
        socketOutputStrm = new PrintWriter(clientSocket.getOutputStream(), true);
        socketInputStrm = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        setName("Receiver");
        register();

    }

    /**
     * Starten the client Thread for receiving messages
     */
    public void run() {
        try {
            receivedMessage();
        } catch (Exception e) {
            LOG.warn("Problem to read socket input: ", e);
        }
    }

    /**
     * Register the client and set the name of the participant
     */
    public void register() {
        Message message;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write your name");
        String name = scanner.nextLine();
        message = new Message(Message.MessageType.REGISTER, name, "Server");
        if (clientSocket.isConnected() && !clientSocket.isClosed()) {
            socketOutputStrm.println(message.toJSON());
        } else {
            throw new RuntimeException("Client is not connected anymore !!!");
        }

    }

    /**
     * Send a message to other participant or server based on the message type
     */
    public void sendMessage() {
        Message message;
        String messageData = "";
        Message.MessageType messageType;
        String recipient = "";
        Scanner scanner = new Scanner(System.in);

        while (loopExit) {
            System.out.println("Select messageType \n" +
                    "1. " + Message.MessageType.MESSAGE_ALL + "\n" +
                    "2. " + Message.MessageType.MESSAGE_PRIVATE + "\n" +
                    "3. " + Message.MessageType.EXIT);
            int input = Integer.parseInt(scanner.nextLine());

            if (input == 1){
            messageType = Message.MessageType.MESSAGE_ALL;
            }else if (input == 2){
                messageType = Message.MessageType.MESSAGE_PRIVATE;
            }else {
                messageType = Message.MessageType.EXIT;
            }

            if (!messageType.equals(Message.MessageType.EXIT)){
                System.out.println("Write message");
                messageData = scanner.nextLine();

                if (messageType.equals(Message.MessageType.MESSAGE_PRIVATE)){
                    System.out.println("What is the name of the recipient?");
                    recipient = scanner.nextLine();
                }
            }else {
                loopExit = false;
            }

            message = new Message(messageType, messageData, recipient);
            if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                System.out.println("Send <" + message + ">");
                socketOutputStrm.println(message.toJSON());
            } else {
                throw new RuntimeException("Client is not connected anymore !!!");
            }
        }
    }

    /**
     * Getting the input from the input socket and print out the received message
     */
    public void receivedMessage() throws IOException {
        while (loopExit) {
            LOG.debug("Wait for message...");
            String message = socketInputStrm.readLine();
            Message msg = Message.fromJSON(message);
            if (message == null) {
                LOG.debug("No Message received");
            } else {
                System.out.println(msg.getData());
            }
        }
    }

    /**
     * Closes the client socket to break the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("192.168.51.50", 4444);
        client.start();
        client.sendMessage();
    }

    @Override
    public void Printer(Message message) {
        System.out.println("Received message: " + message.getData());
    }
}
