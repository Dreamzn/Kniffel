package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandler;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageRegister extends AbstractMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageRegister.class);

    public MessageRegister() {
        super(Message.MessageType.REGISTER);
    }

    @Override
    public void handle(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        client.setParticipant(message.getData());
        for (MessageHandlingClient messageHandlingClient :server.getConnectedClients()) {
            if(!messageHandlingClient.getParticipant().equals(client.getParticipant())){
                client.sendMessage(new Message(Message.MessageType.MESSAGE_ALL, "Client" + client.getParticipant() +" joined the server", "All"));
            }
        }
    }
}
