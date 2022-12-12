package net.atos.kniffel.Network;

import java.io.IOException;
import java.net.Socket;

public interface ServerClientConnection {

    Socket startConnection() throws IOException;

}
