package net.atos.kniffel.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerClientHandler extends Thread{
    private Socket clientSocket;
    private PrintWriter clientSocketOutputStream;
    private BufferedReader clientSocketInputStream;
    private List<ServerClientHandler> peerClientHandler = new ArrayList<>();

    public ServerClientHandler(String clientName, Socket socket) throws IOException {
        this.clientSocket = socket;
        this.clientSocketOutputStream = new PrintWriter(clientSocket.getOutputStream(), true);
        this.clientSocketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.setName(clientName);
    }

    public void addPeerClientHandler(ServerClientHandler peerClient) {
        peerClientHandler.add(peerClient);
    }

    public void sendMessage(String message) {
        clientSocketOutputStream.println(message);
    }

    public String receiveMessage() throws IOException {
        System.out.println(this.getName() + " - Waiting to read data from socket...");
        String inputLine = clientSocketInputStream.readLine();
        return inputLine;
    }

    public void run() {
        try {
            String inputLine;

            do {
                inputLine = receiveMessage();
                for (ServerClientHandler peerClient : peerClientHandler) {
                    peerClient.sendMessage(inputLine);
                    System.out.println(this.getName() + " - Send message ::= [" + inputLine + "] to client ::= [" + peerClient.getName() + "]");
                }
            } while (inputLine != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
