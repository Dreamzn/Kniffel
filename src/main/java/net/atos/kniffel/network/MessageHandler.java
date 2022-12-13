package net.atos.kniffel.network;

public interface MessageHandler {

    void handleMessage(MessageHandlingServer server, MessageHandlingClient client, Message message);

}
