package net.atos.kniffel.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServerTest {
    public void start(int port) throws IOException {
        ServerSocket serverSocketA = new ServerSocket(port);
        Socket clientSocketA = serverSocketA.accept(); // blocking

        System.out.println("Established connection to client A, create input and output streams...");
        PrintWriter outA = new PrintWriter(clientSocketA.getOutputStream(), true);
        BufferedReader inA = new BufferedReader(new InputStreamReader(clientSocketA.getInputStream()));
        System.out.println("Start reading data from socket...");
        String inputLineA = inA.readLine();



        ServerSocket serverSocketB = new ServerSocket(port + 1);
        Socket clientSocketB = serverSocketB.accept();
        System.out.println("Established connection to client B, create input and output streams...");
        PrintWriter outB = new PrintWriter(clientSocketA.getOutputStream(), true);
        BufferedReader inB = new BufferedReader(new InputStreamReader(clientSocketA.getInputStream()));
        System.out.println("Start reading data from socket B...");
        String inputLineB = inB.readLine();

        while ((inputLineA != null || inputLineB != null)) {
            System.out.println("Received message from input ::= [" + inputLineA + "]");

            if (".".equals(inputLineA)) {
                System.out.println("Received termination signal, close socket.");
                outA.println("good bye");
                break;
            }

            outA.println(inputLineA);
        }
    }

    public static void main(String[] args) throws IOException {
        MultiServerTest server = new MultiServerTest();
        server.start(4444);
    }
}
