package simulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.IOException;

public class CoordinatesMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        Coordinates coordinates = (Coordinates) object;
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding("UTF-8");
        try {
            return new Message(new ObjectMapper().writeValueAsBytes(coordinates), messageProperties);
        } catch (JsonProcessingException e) {
            throw new MessageConversionException("Failed to convert Coordinates to message", e);
        }
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        try {
            return new ObjectMapper().readValue(message.getBody(), Coordinates.class);
        } catch (IOException e) {
            throw new MessageConversionException("Failed to convert message to Coordinates.", e);
        }
    }
}
