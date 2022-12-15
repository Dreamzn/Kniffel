package net.atos.kniffel.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String ip;


    private int port;

    private Socket clientSocket;
    private PrintWriter socketOutputStrm;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() throws IOException {
        clientSocket = new Socket(ip, port);
        clientSocket.setSoTimeout(0);
        socketOutputStrm = new PrintWriter(clientSocket.getOutputStream(), true);

        register();
        sendMessage();

        clientSocket.close();
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
                System.out.println("Send <" + message + "> to the SERVER");
                socketOutputStrm.println(message.toJSON());
            } else {
                throw new RuntimeException("Client is not connected anymore !!!");
            }
        }
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception {

        Client client = new Client("127.0.0.1", 4444);
        client.start();

    }
}
