package bloodcenter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfigNotification {
    public static final String QUEUE = "notification_queue";
    public static final String EXCHANGE = "notification_exchange";
    public static final String ROUTING_KEY = "notification_routingKey";
    @Bean
    public Queue notificationQueue(){
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange notificationExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding notificationBinding(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter notificationMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate notificationTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(notificationMessageConverter());
        return template;
    }
}
