package net.atos.kniffel.network.handler;

import net.atos.kniffel.network.Message;
import net.atos.kniffel.network.MessageHandler;
import net.atos.kniffel.network.MessageHandlingClient;
import net.atos.kniffel.network.MessageHandlingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutPrintMessageHandler implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OutPrintMessageHandler.class);

    @Override
    public void handleMessage(MessageHandlingServer server, MessageHandlingClient client, Message message) {
        LOG.info("Received message {} from client {}", message, client);
    }
}
