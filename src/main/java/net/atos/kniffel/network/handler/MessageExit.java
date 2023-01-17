package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import net.atos.kniffel.network.OutPrintInAndOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.LinkOption;

/**
 * A subclass of the AbstractMessageHandler to send a message to all clients that a client is leaving, if the messageType is like messageExit
 */
public class MessageExit extends AbstractMessageHandler {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageExit.class);

    /**
     * Create a new MessageExit handler
     */
    public MessageExit() {
        super(Message.MessageType.EXIT);
    }

    /**
     * Sends a message to all connected clients except of itself, that he is leaving if the messageType ist messageExit
     * @param server to get connected clients
     * @param client to get participant
     * @param message to handle
     */
    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        try {
            client.closeSocket();


        } catch (IOException e) {
            LOG.warn("Failed to close the socket: ",e);
        }
        for (MessageHandlingClient messageHandlingClient :server.getConnectedClients()) {
            if(!messageHandlingClient.getParticipant().equals(client.getParticipant())){
                messageHandlingClient.sendMessage(new Message(Message.MessageType.MESSAGE_ALL, "Client " + client.getParticipant() +" disconected", "All"));
            }
        }
    }

    @Override
    public void Printer(Message message) {
    }
}
