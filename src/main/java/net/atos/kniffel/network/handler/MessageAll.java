package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import net.atos.kniffel.network.OutPrintInAndOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A subclass of the AbstractMessageHandler to send a message to all clients, if the messageType is like messageAll
 */
public class MessageAll extends AbstractMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MessageAll.class);

    /**
     * New instance of MessageAll handler
     */
    public MessageAll() {
        super(Message.MessageType.MESSAGE_ALL);
    }

    /**
     * Sends the message to all connected clients
     * @param server to get connected clients
     * @param client to get participants
     * @param message to handle
     */
    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        for (MessageHandlingClient messageHandlingClient : server.getConnectedClients()) {

            if (messageHandlingClient.getParticipant() != null) {
                Message msgToSend = new Message(Message.MessageType.MESSAGE_ALL, client.getParticipant() + ": " + message.getData(), "");
                Printer(msgToSend);
                messageHandlingClient.sendMessage(msgToSend);
            }

        }
    }


    @Override
    public void Printer(Message message) {
        System.out.println("Send message: " + message.getData());
    }
}
