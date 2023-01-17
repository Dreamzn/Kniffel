package net.atos.kniffel.network;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Message has messageTypes which decide who the message is going to receive
 * and converting the message from an object to a string and back, for the data transfer
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Message implements net.atos.kniffel.network.OutPrintInAndOutput {

    /**
     * Logger
     */

    private static final Logger LOG = LoggerFactory.getLogger(Message.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();




    /**
     * MessageTypes which decides to whom the message is transfer to
     */
    public enum MessageType {
        REGISTER,
        MESSAGE_ALL,
        MESSAGE_PRIVATE,
        EXIT
    }

    /**
     * MessageTypes which decides to whom the message is transfer to
     */
    private MessageType type;
    /**
     * String input of the message
     */
    private String data;
    /**
     * The recipient
     */
    private String recipient;


    /**
     * Create a new instance of a message. A message has a messageType, a String with the actual message and the recipient
     * @param messageType
     * @param data
     * @param recipient
     */
    public Message(MessageType messageType, String data, String recipient){
        this.type = messageType;
        this.data = data;
        this.recipient = recipient;
    }

    private Message() {
    }

    @Override
    public void Printer(Message message) {

    }

    /**
     * Converting a string to an JSON object(Datatype Message)
     * @param message to convert
     * @return Message
     */
    public static Message fromJSON(String message) {
        try {
            return OBJECT_MAPPER.readValue(message, Message.class);
        } catch (Exception e) {
            LOG.warn("Exception doing String conversion to JSON: ", e);
        }
        return null;
    }

    //Object to string

    /**
     * Convert a JSON object(MessageType) to a string
     * @return String
     */
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

    /**
     * Get the messageType of the message
     * @return messageType
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Get the data(String) from the message
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     * Get the Recipient(String) from the message
     */
    public String getRecipient() {
        return recipient;
    }

    public static void main(String[] args) {
        Message message = new Message(MessageType.MESSAGE_ALL,"Hallo","Leon");
        System.out.println(message.toJSON());
        System.out.println(fromJSON(message.toJSON()));

    }
}
