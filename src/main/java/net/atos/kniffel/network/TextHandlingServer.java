package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TextHandlingServer extends AbstractServer {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(TextHandlingServer.class);


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

                    LOG.debug("Wait for message...");
                    message = socketInputStrm.readLine();
                    LOG.debug("Received message: {}", message);


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
