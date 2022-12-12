package net.atos.kniffel.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TextHandlingServer extends AbstractServer {

    public TextHandlingServer(int port) {
        super(port);
    }

    @Override
    protected Thread createServerClientThread(Socket clientSocket) {
        return new Thread(() -> {
            String message = null;
            try {
                while (true) {

                    BufferedReader socketInputStrm = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    System.out.println("Wait for message...");
                    message = socketInputStrm.readLine();
                    System.out.println("Received message: " + message);

                    if (message.equals("close")) {
                        clientSocket.close();
                        return;
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        TextHandlingServer server = new TextHandlingServer(4444);
        server.start();
    }

}
