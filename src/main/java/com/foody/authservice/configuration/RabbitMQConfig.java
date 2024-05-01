package com.foody.authservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String AUTH_DELETE_QUEUE = "user_auth_delete_queue";
    public static final String AUTH_QUEUE = "auth_queue";
    public static final String DIRECT_EXCHANGE = "direct_user_exchange";
    public static final String CREATE_ROUTING_KEY = "create_user_routingKey";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue auth_user_delete_queue() {
        return new Queue(AUTH_DELETE_QUEUE);
    }

    @Bean
    public Queue auth_user_queue() {
        return new Queue(AUTH_QUEUE);
    }

    @Bean
    public Binding bindingAuthQueueUserCreate(Queue auth_user_queue, DirectExchange exchange) {
        return BindingBuilder.bind(auth_user_queue)
                .to(exchange)
                .with(CREATE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
