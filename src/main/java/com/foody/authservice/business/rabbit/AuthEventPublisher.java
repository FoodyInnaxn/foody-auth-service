package com.foody.authservice.business.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.authservice.configuration.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthEventPublisher {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void publishCreatedAuthEvent(UserToSend event) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.CREATE_ROUTING_KEY, createMessage(event));
    }

    private Message createMessage(Object request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.setContentEncoding("UTF-8");
        Message msg = new Message(objectMapper.writeValueAsBytes(request), messageProperties);
        return msg;
    }

}
