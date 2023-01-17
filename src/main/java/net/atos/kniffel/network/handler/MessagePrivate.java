package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import net.atos.kniffel.network.OutPrintInAndOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A subclass of the AbstractMessageHandler to send a message to a certain client, if the messageType is like messagePrivate
 */
public class MessagePrivate extends AbstractMessageHandler {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessagePrivate.class);

    /**
     * Create a new MessagePrivate handler
     */
    public MessagePrivate() {
        super(Message.MessageType.MESSAGE_PRIVATE);
    }


    /**
     * Sends a message to a certain client
     *
     * @param server
     * @param client  to get participant
     * @param message to handle
     */
    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        for (MessageHandlingClient messageHandlingClient : server.getConnectedClients()) {
            if (message.getRecipient().equals(messageHandlingClient.getParticipant())) {
                Printer(message);
                messageHandlingClient.sendMessage(message);
            }
        }
    }

    @Override
    public void Printer(Message message) {
        System.out.println("Send message: " + message.getData());
    }
}
