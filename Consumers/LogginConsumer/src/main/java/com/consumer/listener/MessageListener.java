/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.consumer.listener;

import com.util.tools.payloads.request.LoginRequest;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author ronya
 */
@Component
@RabbitListener(queues = "${producer.logging.queuename}")
public class MessageListener {

    @RabbitHandler
    public void listener(LoginRequest message) {
        
            System.out.println(message.getPassword());
        
    }

}
