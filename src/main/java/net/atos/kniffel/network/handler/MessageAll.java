package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageAll extends AbstractMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MessageAll.class);
    public MessageAll() {
        super(Message.MessageType.MESSAGE_ALL);
    }

    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        for (MessageHandlingClient messageHandlingClient :server.getConnectedClients()) {
            if(messageHandlingClient.getParticipant().equals(client.getParticipant())){
                continue;
            }else {
                client.sendMessage(message);
            }
        }
    }
}
