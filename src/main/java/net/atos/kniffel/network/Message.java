package net.atos.kniffel.network;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Message {

    private static final Logger LOG = LoggerFactory.getLogger(Message.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();



    public enum MessageType {
        REGISTER,
        MESSAGE_ALL,
        MESSAGE_PRIVATE,
        EXIT
    }

    private MessageType type;

    private String data;

    private String recipient;


    public Message(MessageType messageType, String data, String recipient){
        this.type = messageType;
        this.data = data;
        this.recipient = recipient;
    }

    public Message(){}

    //String to Object
    public static Message fromJSON(String message) {
        try {
            return OBJECT_MAPPER.readValue(message, Message.class);
        } catch (Exception e) {
            LOG.warn("Exception doing String conversion to JSON: ", e);
        }
        return null;
    }

    //Object to string
    public String toJSON() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            LOG.warn("Exception doing JSON conversion to String: ", e);
        }
        return null;
    }

    public String toString() {
        return "Message{" +
                "type=" + type +
                ", data='" + data + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }

    public MessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getRecipient() {
        return recipient;
    }

    public static void main(String[] args) {
        Message message = new Message(MessageType.MESSAGE_ALL,"Hallo","Leon");
        System.out.println(message.toJSON());
        System.out.println(fromJSON(message.toJSON()));

    }
}
