package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMessageHandler implements MessageHandler, OutPrintInAndOutput {

    /**
     * MessageType of the message
     */
    private Message.MessageType messageType;

    /**
     * New instance of a AbstractMessageHandler
     * @param messageType to handle the message
     */
    public AbstractMessageHandler(Message.MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Handels the incomming message based on the messageType
     * @param server to get message handler from
     * @param client to check the participant
     * @param message to be handelt
     */
    @Override
    public void handleMessage(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        if (message.getType().equals(this.messageType)) {
            handle(server, client, message);
        } else {
            return;
        }
    }

    /**
     * The abstract handle method
     * @param server to get connected clients
     * @param client to get participant
     * @param message to handle
     */
    protected abstract void handle(MessageHandlingServer server, MessageHandlingClient client, Message message);
}
