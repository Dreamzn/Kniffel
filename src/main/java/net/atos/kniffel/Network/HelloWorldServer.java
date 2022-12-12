package net.atos.kniffel.Network;

import java.io.IOException;
import java.net.Socket;

public class HelloWorldServer extends AbstractServer {

    public HelloWorldServer(int port) {
        super(port);
    }

    @Override
    protected Thread createServerClientThread(Socket clientSocket) {
        return new Thread(() -> {
            System.out.println("Hello World ");
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        HelloWorldServer server = new HelloWorldServer(4444);
        server.start();
    }

}
