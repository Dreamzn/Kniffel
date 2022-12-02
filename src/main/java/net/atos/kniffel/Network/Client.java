package net.atos.kniffel.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter socketOutputStrm;

    private String clientName = "Test";


    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        clientSocket.setSoTimeout(0);
        socketOutputStrm = new PrintWriter(clientSocket.getOutputStream(), true);

        ServerClientHandler serverClientHandler = new ServerClientHandler(clientName, clientSocket);
    }

    public void sendMessage() {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Send a message");
            String message = scanner.nextLine();
            socketOutputStrm.println(message);
        }
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
    }


    public static void main(String[] args) throws Exception {

        Client client = new Client();
        client.startConnection("127.0.0.1", 4444);

    }
}
