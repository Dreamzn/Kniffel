package net.atos.kniffel.network;

public class Message {

    public enum MessageType {
        REGISTER,
        MESSAGE_ALL,
        MESSAGE_PRIVATE,
        EXIT
    }

    private MessageType type;

    private String data;

    private String recipient;


    public static Message fromJSON(String message) {
        return null;
    }

    public String toJSON() {

    }


}
