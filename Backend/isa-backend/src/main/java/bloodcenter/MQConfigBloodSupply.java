package bloodcenter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfigBloodSupply {
    public static final String QUEUE = "blood_queue";
    public static final String EXCHANGE = "blood_exchange";
    public static final String ROUTING_KEY = "blood_routingKey";
    @Bean
    public Queue bloodQueue(){
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange bloodExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bloodBinding(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter bloodMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate bloodTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(bloodMessageConverter());
        return template;
    }
}
