package net.atos.kniffel.network;

import java.io.BufferedReader;
import java.io.IOException;

public class TextHandling extends Thread {
    private BufferedReader socketInputStrm;

    public TextHandling(BufferedReader socketInputStrm) {
        this.socketInputStrm = socketInputStrm;
    }

    public void run() {
        String resp = null;
        try {
            while (true) {
                System.out.println("Wait for a response...");
                resp = socketInputStrm.readLine();
                System.out.println("Received message: " + resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
