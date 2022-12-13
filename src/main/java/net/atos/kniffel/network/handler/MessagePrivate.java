package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagePrivate extends AbstractMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePrivate.class);
    public MessagePrivate() {
        super(Message.MessageType.MESSAGE_PRIVATE);
    }

    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        if (message.getRecipient().equals(client.getParticipant())){
            client.sendMessage(message);
        }
    }
}
