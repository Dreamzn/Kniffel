package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.LinkOption;

public class MessageExit extends AbstractMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MessageExit.class);
    public MessageExit() {
        super(Message.MessageType.EXIT);
    }

    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        try {
            client.closeSocket();
        } catch (IOException e) {
            LOG.warn("Failed to close the socket: ",e);
        }
        for (MessageHandlingClient messageHandlingClient :server.getConnectedClients()) {
            if(messageHandlingClient.getParticipant().equals(client.getParticipant())){
                continue;
            }else {
                client.sendMessage(new Message(Message.MessageType.MESSAGE_ALL, "Client" + client.getParticipant() +" disconected", "All"));
            }
        }
    }
}
