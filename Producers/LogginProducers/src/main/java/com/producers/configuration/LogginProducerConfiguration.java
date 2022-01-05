/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.producers.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ronya
 */
@Configuration
public class LogginProducerConfiguration {

    @Bean
    public LogginProducerProperties logginProduceProperties() {
        return new LogginProducerProperties();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() throws Exception {
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(logginProduceProperties().getHost());
        connectionFactory.setPort(logginProduceProperties().getPort());
        connectionFactory.setUsername(logginProduceProperties().getUser());
        connectionFactory.setPassword(logginProduceProperties().getPassword());
        connectionFactory.setVirtualHost(logginProduceProperties().getVirtualhost());

        return connectionFactory;

    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;

    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        boolean durable = true;
        boolean exclusive = false;
        boolean autoDelete = false;
        return new Queue(logginProduceProperties().getQueueName(), durable, exclusive, autoDelete);

    }

    @Bean
    public TopicExchange exchange() {
        boolean durable = true;
        boolean autoDelete = false;
        return new TopicExchange(logginProduceProperties().getExchange(), durable, autoDelete);

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).
                to(exchange()).
                with(logginProduceProperties().getRoutingKey());

    }
}
