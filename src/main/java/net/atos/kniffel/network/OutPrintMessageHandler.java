package net.atos.kniffel.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutPrintMessageHandler implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OutPrintMessageHandler.class);

    @Override
    public void handleMessage(MessageHandlingServer server, MessageHandlingClient client, String message) {
        LOG.info("Send message {} to server {} from client {}", message, server, client);
    }
}
