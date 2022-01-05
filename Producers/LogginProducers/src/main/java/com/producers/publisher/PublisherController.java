/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.producers.publisher;

import com.producers.configuration.LogginProducerProperties;
import com.util.tools.payloads.request.LoginRequest;
import com.util.tools.payloads.response.MessageRegisterResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ronya
 */
@RestController
@RequestMapping("/api/auth")
public class PublisherController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    LogginProducerProperties logginProducerProperties;

    @PostMapping("/singIn")
    public ResponseEntity<?> registerLogginMQMessageOnServerFromRestApi(@RequestBody LoginRequest logginRequest) {
        MessageRegisterResponse response = new MessageRegisterResponse();

        rabbitTemplate.convertAndSend(logginProducerProperties.getExchange(), logginProducerProperties.getRoutingKey(), logginRequest);
        // response.setCode("200");
        response.setMessage("succsess");
        response.setStatus("ok");
        return ResponseEntity.ok(response);

    }

}
