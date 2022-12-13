package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandler;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMessageHandler implements MessageHandler {


    private Message.MessageType messageType;

    public AbstractMessageHandler(Message.MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public void handleMessage(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        if (message.getType().equals(this.messageType)) {
            handle(server, client, message);
        } else {
            return;
        }
    }

    protected abstract void handle(MessageHandlingServer server, MessageHandlingClient client, Message message);
}
