package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);
    private String ip;


    private int port;

    private Socket clientSocket;
    private PrintWriter socketOutputStrm;

    private BufferedReader socketInputStrm;

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

    public void run() {
        try {
            receivedMessage();
        }catch (Exception e){
            LOG.warn("Problem to read socket input: ", e);
        }
    }

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

    public void sendMessage() {
        Message message;
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Write message");
            String messageToSend = scanner.nextLine();
            message = new Message(Message.MessageType.MESSAGE_ALL, messageToSend, "All");
            if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                System.out.println("Send <" + message + ">");
                socketOutputStrm.println(message.toJSON());
            } else {
                throw new RuntimeException("Client is not connected anymore !!!");
            }
        }
    }

    public void receivedMessage() throws IOException {
        while (true) {
            LOG.debug("Wait for message...");
            String message = socketInputStrm.readLine();
            if (message == null) {
                LOG.debug("No Message received");

            }else {
                LOG.info("Received message: " + message);
            }

        }
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception {

        Client client = new Client("127.0.0.1", 4444);
        client.start();
        client.sendMessage();
    }
}
