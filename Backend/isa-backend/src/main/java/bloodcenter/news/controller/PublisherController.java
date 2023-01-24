package bloodcenter.news.controller;

import bloodcenter.MQConfig;
import bloodcenter.news.model.NewsContent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/news")
public class PublisherController {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/publish")
    public void publishMessage(@RequestBody NewsContent message){
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
    }
}
