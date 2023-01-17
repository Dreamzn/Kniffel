package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A subclass of the AbstractMessageHandler to send a message to the server to register the participants name and to all connected clients,
 * that a participant has joined,
 * if the messageType is ike messageRegister
 */
public class MessageRegister extends AbstractMessageHandler{
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(MessageRegister.class);

    /**
     * Create a new MessageRegister handler
     */
    public MessageRegister() {
        super(Message.MessageType.REGISTER);
    }

    /**
     * Sets the participant name of the client and sends a message to all connectet clients that a new participant has joined
     * @param server to get connected clients
     * @param client to get participant
     * @param message to handle
     */
    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        client.setParticipant(message.getData());
        for (MessageHandlingClient messageHandlingClient : server.getConnectedClients()) {
            if (messageHandlingClient.getParticipant() != null && !messageHandlingClient.getParticipant().equals(client.getParticipant())) {
                Message msgToSend = new Message(Message.MessageType.MESSAGE_ALL, "Client " + client.getParticipant() + " joined the server", "All");
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
